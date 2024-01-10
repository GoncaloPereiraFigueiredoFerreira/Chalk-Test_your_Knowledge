#This code is for v1 of the openai package: pypi.org/project/openai
from openai import OpenAI
import tiktoken
import json 

import os
from threading import Semaphore, Timer
from functools import singledispatch

client = OpenAI(api_key = os.getenv("API_KEY"))
model = "gpt-3.5-turbo"

requests_sem = Semaphore(int(os.getenv("N_REQ",3)))  

def send_request(message,temperature = 0,presence = 0,frequency = 0):
    requests_sem.acquire()
    tokens = 1000 #Todo 

    stream = client.chat.completions.create(
          model = model,
          messages = message,
          temperature = temperature,
          max_tokens = tokens,
          top_p = 1,
          frequency_penalty = frequency,
          presence_penalty = presence
        )

    Timer(60,lambda: requests_sem.release()).start()

    return stream.choices[0].message.content

def send_request_json(message,temperature = 0,presence = 0,frequency = 0):
    count = 0
    flag = True
    ret = None

    while count < 3 and flag:
        try:
            ret = send_request(message,temperature,presence,frequency)
            ret = json.loads(ret)
            flag = False
        except:
            count= count + 1

    return ret

def num_tokens_from_messages(messages):
  """Returns the number of tokens used by a list of messages."""
  try:
      encoding = tiktoken.encoding_for_model(model)
  except KeyError:
      encoding = tiktoken.get_encoding("cl100k_base")
  if model == "gpt-3.5-turbo-0613" or True:  # note: future models may deviate from this
      num_tokens = 0
      for message in messages:
          num_tokens += 4  # every message follows <im_start>{role/name}\n{content}<im_end>\n
          for key, value in message.items():
              num_tokens += len(encoding.encode(value))
              if key == "name":  # if there's a name, the role is omitted
                  num_tokens += -1  # role is always required and always 1 token
      num_tokens += 2  # every reply is primed with <im_start>assistant
      return num_tokens
  else:
      raise NotImplementedError(f"""num_tokens_from_messages() is not presently implemented for model {model}.
  See https://github.com/openai/openai-python/blob/main/chatml.md for information on how messages are converted to tokens.""")

def send_open_answer(answer,question,answer_critiria,answer_topics = None,answer_auxiliar = None):
    ret = []

    sys_pronpt = gen_sys_open_answer(question,answer_critiria,answer_topics,answer_auxiliar)

    user_pronpt = gen_user_open_answer(answer)
    resp = send_request_json([sys_pronpt,user_pronpt[0]])
    ret.append(resp["category"])

    return ret

def send_create_mult(text,questions,user_input = ""):
    text = gen_sys_create_question(text)
    questions = gen_user_create_multiple(user_input,questions)

    pronpt = [text] + questions

    resp = send_request_json(pronpt,1,0.5,0.5)

    return resp

def send_create_open(text,questions,user_input = ""):
    text = gen_sys_create_question(text)
    questions = gen_user_create_open(user_input,questions)

    pronpt = [text] + questions

    print(pronpt)

    resp = send_request_json(pronpt,1,0.5,0.5)
    print(resp)

    return resp

def send_create_true_false(text,questions,user_input = ""):
    text = gen_sys_create_question(text)
    questions = gen_user_create_true_false(user_input,questions)

    pronpt = [text] + questions

    resp = send_request_json(pronpt,1,0.5,0.5)

    return resp

def send_oral(text):
    resp = send_request(text,1,0.5,0.5)
    return resp

def send_eval_oral(topics,text):
    sys_pronpt = {"role":"system","content":gen_sys_eval_oral(topics)}

    resp = send_request_json([sys_pronpt] + text)

    return resp


def gen_sys_open_answer(question,answer_critiria,answer_topics = None,answer_auxiliar = None):
    topics = ""
    if topics:
        topics = "Check if the following pieces of information are directly contained in the answer:\n"
        for i in answer_topics:
            topics = topics + "- " + i + "\n"

    criteria = "Check if the answer can be placed in one of the following numbered categories,considering the topics above:\n"
    for i in range(len(answer_critiria)):
        criteria = criteria + str(i) + "- " + answer_critiria[i] + "\n"

    auxiliar = ""
    if answer_auxiliar:
        auxiliar = "Consider the following pieces of information, delimited by double quotes, in your answer.\"\"{}\"\"".format(answer_auxiliar)

    ret = '''{auxiliar}You will be provided with text delimited by triple quotes that is supposed to be the answer to the question \"{question}\".
{topics}{criteria}Finally, provide the category in which the question fits. Provide this categorie as {{"category": <insert category here>}}.'''.format(question = question, topics = topics, auxiliar = auxiliar, criteria = criteria)

    ret = {"role":"system","content":ret}

    return ret

@singledispatch
def gen_user_open_answer(resp):
    return None

@gen_user_open_answer.register
def _(resp:str):
    return [{"role":"user","content":'"""' + resp + '"""'}]

@gen_user_open_answer.register
def _(rest:list):
    ret = []
    for i in resp:
        ret.append(gen_user_open_answer(i))
    return ret

def gen_sys_create_question(text):
    return {"role":"system",
    "content":"You will be prompt to generate questions about the following text \"{}\".All questions must be in the text language. ".format(text)}

def gen_user_create_multiple(user_input,questions = None):
    ret = []

    if questions:
        for i in questions:
            answers = []
            if "Answers" in i:
                answers = i["Answers"]
            aux = {"Question":i["Question"],"Answers":answers}
            ret.append({"role":"assistant","content":json.dumps(aux)})

    ret.append({"role":"user","content":user_input + '.Generate a multiple choice question. The response must be in json, where there is a key "Question", "Answers", that is an array, and "Correct_answer" that is the position in "answers".'})

    return ret

def gen_user_create_open(user_input,questions = None):
    ret = []

    if questions:
        for i in questions:
            topics = []
            if "Topics" in i:
                topics = i["Topics"]
            aux = {"Question":i["Question"],"Topics":topics}
            ret.append({"role":"assistant","content":json.dumps(aux)})

    ret.append({"role":"user","content":user_input + '.Generate a open answer question and topics that must be covered in the answer. The response must be in json, where there is a key "Question" and "Topics".'})

    return ret

def gen_user_create_true_false(user_input,questions = None):
    ret = []

    if questions:
        for i in questions:
            aux = {"Question":i["Question"]}
            ret.append({"role":"assistant","content":json.dumps(aux)})

    ret.append({"role":"user","content":user_input + '.Generate a True or False question. The response must be in json, with this format {"Question":question,"True":True or False}.'})

    return ret

def gen_sys_oral(topics):
    ret = "Consider the following topics:\n{}You will be provided answers from a user, and you must generate questions to ensure the student addresses the topics given, based on his answers."
    aux = ""

    for i in topics:
        aux = aux + "- " + i + "\n"

    return {"role":"system","content":ret.format(aux)}

def gen_ass_oral(question):
    return {"role":"assistant","content":question}

def gen_user_oral(answer):
    return {"role":"user","content":answer}

def gen_sys_eval_oral(topics):
    ret = "Consider the following topics:\n{}You will be provided with question and answers referent to the topics presented. You must evaluate the answers from 0 to 10 based on who the answers cover the topics presented.\nThe output must only be in json, with the format {{\"Cotation\": evaluation}}. "
    aux = ""

    for i in topics:
        aux = aux + "- " + i + "\n"

    return ret.format(aux)

'''
Consider the folling rules for the use of commas:
- separar o nome do local da data;
- separar os elementos de uma enumeração;
- isolar o vocativo;
- isolar o modificador do nome apositivo, seja ele de natureza adjetival, preposicional ou oracional (orações subordinadas adjetivas relativas explicativas);
- isolar palavras ou expressões intencionalmente repetidas em construções de intensificação;
- indicar a elipse de um verbo em orações com uma estrutura paralela àquelas que as antecedem;
- isolar palavras, expressões ou orações intercaladas na frase;
- separar orações coordenadas (quando aplicável);
- separar orações adverbiais, finitas ou não finitas, quando colocadas antes da subordinante ou nela sãointercaladas.

You will be provided with text delimited by triple quotes that is supposed to be the answer to a question. Check if the following error types appear:

"Tipo A":
- erro inequívoco de pontuação
- erro de ortografia (incluindo erro de acentuação, erro de translineação e uso indevido de letra minúscula ou de letra maiúscula)
- erro de morfologia
- incumprimento das regras de citação de texto ou de referência a título de uma obra

"Tipo B":
- erro de sintaxe
- impropriedade lexical

For each type of error "Tipo A" and "Tipo B", count how many there were in the answer. 
Finally, provide a count of how many errors there are. Provide this count as {"Tipo A": <insert count of "Tipo A" here>,"Tipo B": <insert count of "Tipo B" here>}.
'''
'''
{
    "Question": "No poema, está presente um «eu», D. Sebastião, que se dirige a um «vós», os Portugueses.Explicite o apelo feito na primeira estrofe e, com base nesse apelo, infira os sentimentos desse «eu» e desse «vós».",
    "Topics": [
        "Explicitação do apelo que D. Sebastião dirige aos portugueses: incita-os a aguardar o seu regresso messiânico (no pressuposto de que a morte física é um momento transitório em que os sonhos «são Deus» – v. 4).",
        "Identificação dos sentimentos do «eu» e do «vós»: D. Sebastião revela confiança na concretização dos seus sonhos (apesar da sua morte); o apelo de D. Sebastião permite inferir que os portugueses manifestam desânimo/descrença, o que leva o rei a incutir-lhes um sentimento de esperança no seu regresso/naquilo de que ele é o símbolo."
    ],
    "Rubric": [
        {
            "Description": "Explicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando, adequadamente, os dois tópicos de resposta. Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas, asseguram de modo global a progressão e o encadeamento das ideias.",
            "Cotation": 10.0
        },
        {
            "Description": "Explicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando, adequadamente, os dois tópicos de resposta. Utiliza mecanismos de coesão textual com falhas que comprometem de modo global a progressão e o encadeamento das ideias.\nOU\nExplicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando os dois tópicos de resposta, um adequadamente e outro com pequenas imprecisões e/ou omissões. Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas, asseguram de modo global a progressão e o encadeamento das ideias.",
            "Cotation": 8.0
        },
        {
            "Description": "Explicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando os dois tópicos de resposta, um adequadamente e outro com pequenas imprecisões e/ou omissões. Utiliza mecanismos de coesão textual com falhas que comprometem de modo global a progressão e o encadeamento das ideias.\nOU\nExplicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando os dois tópicos de resposta, ambos com pequenas imprecisões e/ou omissões. Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas, asseguram de modo global a progressão e o encadeamento das ideias.\nOU\nAborda, adequadamente, apenas um dos tópicos de resposta. Utiliza mecanismos de coesão textual que, apesar da eventual ocorrência de falhas, asseguram de modo global a progressão e o encadeamento das ideias.",
            "Cotation": 6.0
        },
        {
            "Description": "Explicita o apelo feito na primeira estrofe e infere os sentimentos do «eu» e do «vós», abordando os dois tópicos de resposta, ambos com pequenas imprecisões e/ou omissões. Utiliza mecanismos de coesão textual com falhas que comprometem de modo global a progressão e o encadeamento das ideias.\nOU\nAborda, adequadamente, apenas um dos tópicos de resposta. Utiliza mecanismos de coesão textual com falhas que comprometem de modo global a progressão e o encadeamento das ideias.",
            "Cotation": 4.0
        },
        {
            "Description": "Aborda, com pequenas imprecisões e/ou omissões, apenas um dos tópicos de resposta. Utiliza mecanismos de coesão textual com eventual ocorrência de falhas que comprometem, ou não, a progressão e o encadeamento das ideias.",
            "Cotation": 2.0
        },
        {
            "Description": "Não aborda qualquer dos tópicos de resposta.",
            "Cotation": 0.0
        }
    ],
    "Auxiliar": "D. Sebastião\n\n‘Sperai! Caí no areal e na hora adversa\nQue Deus concede aos seus\nPara o intervalo em que esteja a alma imersa\nEm sonhos que são Deus.\n\nQue importa o areal e a morte e a desventura\nSe com Deus me guardei?\nÉ O que eu me sonhei que eterno dura,\nÉ Esse que regressarei.",
    "Answers": [
        {
            "id_user": 1,
            "Answer": "Na primeira estrofe, D. Sebastião faz um apelo aos Portugueses, pedindo-lhes que esperem com paciência e fé, mesmo em tempos adversos. O \"eu\" (D. Sebastião) expressa esperança e confiança em Deus, apesar da morte e da desventura, enquanto o \"vós\" (os Portugueses) pode estar ansioso e preocupado com a ausência do rei, mas é encorajado a manter a fé e a esperança na sua volta."
        },
        {
            "id_user":2,
            "Answer": "Na primeira estrofe D. Sebastião, o “eu”, faz um apelo aos portugueses, o “vós” e pede-lhes que esperem por ele e que tenham fé no seu regresso. Ele caiu numa situação complicada, caiu “no areal”, mas sugere que isto é temporário, pois Deus está a guardar a sua alma mesmo na \"hora adversa\". Pelo contrário, os portugueses sentem incerteza e preocupação, uma vez que o seu rei está desaparecido. D. Sebastião pede-lhes que aguardem com paciência o seu retorno."
        }
    ]
}
'''
