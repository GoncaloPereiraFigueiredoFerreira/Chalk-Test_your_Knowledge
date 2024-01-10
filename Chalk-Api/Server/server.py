import os
from flask import Flask,request, json
#from flask_api import status
from redis import Redis

import api_ai

api = Flask(__name__)
cache = Redis(host=os.getenv("REDIS","localhost"), port=6379, decode_responses=True)
cache_timeout = 600 # 10 min

'''
{
    "Question": str
    "Topics":[str] #opcional
    "Rubric":[{description:str,cotation:float}]
    "Auxiliar":str #opcional (texto associados a pergunta) #opcional
    "Answer": str
}

{
    {"Evaluation": float}
}
'''

@api.route('/open_answer', methods=['GET'])
def open_answer():
    req = request.json
    question = req["Question"]

    topics = None
    if "Topics" in req:
        topics = req["Topics"]

    criteria = req["Rubric"]

    auxiliar = None
    if "Auxiliar" in req:
        auxiliar = req["Auxiliar"]

    answer = req["Answer"]

    criteria = [(i["Description"],i["Cotation"]) for i in criteria]
    criteria.sort(key = lambda i: i[1])
    criteria_aux = [i[0] for i in criteria]

    cot = api_ai.send_open_answer(answer,question,criteria_aux,topics,auxiliar)

    cot = {"Evaluation":cot}

    return json.dumps(cot)

'''
{
    "Text": str
    "Input": str # opcional Expressa o input do utilizador(Caso seja intruduzido elemina o historico anterior de perguntas)
} 

{
    "Answers": [str],
    "Correct_answer": int, #Indice da resposta correta 
    "Question": str
}
'''

@api.route('/create_mult', methods=['GET'])
def create_mult():
    req = request.json

    h_text = hash(req["Text"])
    user_input = ""
    questions = cache.get(h_text)
    if questions: questions = json.loads(questions)

    if("Input" in req):
        user_input = req["Input"]
        questions = [questions[-1]] if questions else questions


    ret = api_ai.send_create_mult(req["Text"],questions,user_input)

    aux = {i:ret[i] for i in ret if i != "Correct_answer"}
    if questions:
        questions.append(ret)
        questions = questions[1:] if len(questions) > 5 else questions
        cache.set(h_text,json.dumps(questions),cache_timeout)
    else:
        cache.set(h_text,json.dumps([aux]),cache_timeout)

    return ret

'''
{
    "Text": str
    "Input": str # opcional Expressa o input do utilizador(Caso seja intruduzido elemina o historico anterior de perguntas)
} 

{
    "Question": str,
    "Topics": [str]
}
'''

@api.route('/create_open', methods=['GET'])
def create_open():
    req = request.json

    h_text = hash(req["Text"])
    user_input = ""
    questions = cache.get(h_text)
    if questions: questions = json.loads(questions)

    if("Input" in req):
        user_input = req["Input"]
        questions = [questions[-1]] if questions else questions

    ret = api_ai.send_create_open(req["Text"] ,questions, user_input)

    if questions:
        questions.append(ret)
        questions = questions[1:] if len(questions) > 5 else questions
        cache.set(h_text,json.dumps(questions),cache_timeout)
    else:
        cache.set(h_text,json.dumps([ret]),cache_timeout)

    return ret

'''
{
    "Text": str
    "Input": str # opcional Expressa o input do utilizador(Caso seja intruduzido elemina o historico anterior de perguntas)
} 

{
    "True": Bool
    "Question": str
}
'''

@api.route('/create_true_false', methods=['GET'])
def create_true_false():
    req = request.get_json()

    h_text = hash(req["Text"])
    user_input = ""
    questions = cache.get(h_text)
    if questions: questions = json.loads(questions)

    if("Input" in req):
        user_input = req["Input"]
        questions = [questions[-1]] if questions else questions


    ret = api_ai.send_create_true_false(req["Text"],questions,user_input)

    if questions:
        questions.append(ret)
        questions = questions[1:] if len(questions) > 5 else questions
        cache.set(h_text,json.dumps(questions),cache_timeout)
    else:
        cache.set(h_text,json.dumps([ret]),cache_timeout)

    return ret

def hash_oral(ex_id,student_id):
    return ex_id + "-" + "student_id"

def parse_chat(chat,topics):
    ret = []

    ret.append(api_ai.gen_sys_oral(topics))

    for i in range(len(chat)):
        if i%2 == 0:
            ret.append(api_ai.gen_ass_oral(chat[i]))
        else:
            ret.append(api_ai.gen_user_oral(chat[i]))
    return ret

'''
{
    "Topics": [str] 
    "Chat": [str] 
    "StudentID": str
}

{
    "Question":str
}
'''

@api.route('/get_oral',methods=['GET'])
def get_oral():
    req = request.get_json()

    id = req["Topics"] + [req["StudentID"]]
    h = str(hash(str(id)))
    prev_chat = cache.get(h)

    if prev_chat: prev_chat = json.loads(prev_chat)
    else: prev_chat = []

    if (prev_chat and prev_chat[:-1] == req["Chat"]):
        ret = {"Question": prev_chat[-1]}
    else:
        prev = parse_chat(req["Chat"],req["Topics"])
        
        resp = api_ai.send_oral(prev)
        prev.append({"role":"assistant","content":resp})
   
        req["Chat"].append(resp)

        cache.set(h,json.dumps(req["Chat"], ensure_ascii=False),cache_timeout)
        ret = {"Question":resp}

    return ret

'''
{
   "Topics":[str] 
   "Chat":[str]
}

{
    "Cotation": 10
}
'''

@api.route('/eval_oral',methods=['GET'])
def eval_oral():
    req = request.get_json()

    prev = parse_chat(req["Chat"],req["Topics"])

    topics = req["Topics"]
    resp = api_ai.send_eval_oral(topics,prev[1:])

    ret = resp

    return ret

if __name__ == '__main__':
    api.run(host="0.0.0.0")
