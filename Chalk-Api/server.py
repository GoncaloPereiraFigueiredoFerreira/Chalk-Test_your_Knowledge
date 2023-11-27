from flask import Flask,request, json
#from flask_api import status
from redis import Redis

import api_ai

api = Flask(__name__)
cache = Redis(host='localhost', port=6379, decode_responses=True)
cache_timeout = 600 # 10 min

'''
{
    "Question": str
    "Topics":[str] #opcional
    "Rubric":[{description:str,cotation:float}]
    "Auxiliar":str #opcional (texto associados a pergunta) #opcional
    "Answers":[{id_user: ??,answer:str}]
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

    answers = req["Answers"]

    criteria = [(i["Description"],i["Cotation"]) for i in criteria]
    criteria.sort(key = lambda i: i[1])
    criteria_aux = [i[0] for i in criteria]

    answers = [(i["id_user"],i["Answer"]) for i in answers]

    cot = api_ai.send_open_answer(answers,question,criteria_aux,topics,auxiliar)

    cot = [{"id_user":i[0],"Cotation":criteria[i[1]][1]} for i in cot]

    return json.dumps(cot)

'''
{
    "Text": str
} Pode se adicionar outro campo para especificar mais a pergunta
'''

@api.route('/create_mult', methods=['GET'])
def create_mult():
    req = request.json

    h_text = hash(req["Text"])
    questions = cache.get(h_text)
    if questions: questions = json.loads(questions)

    ret = api_ai.send_create_mult(req["Text"],questions)

    aux = {i:ret[i] for i in ret if i != "Correct_answer"}
    if questions:
        cache.set(h_text,json.dumps(questions.append(aux)),cache_timeout)
    else:
        cache.set(h_text,json.dumps([aux]),cache_timeout)

    return ret

@api.route('/create_open', methods=['GET'])
def create_open():
    req = request.json

    h_text = hash(req["Text"])
    questions = cache.get(h_text)
    if questions: questions = json.loads(questions)

    ret = api_ai.send_create_open(req["Text"],questions)

    if questions:
        cache.set(h_text,json.dumps(questions.append(ret)),cache_timeout)
    else:
        cache.set(h_text,json.dumps([ret]),cache_timeout)

    return ret

'''
{
    "Ex_id":str
    "Student_id":str
    "Topics":[str]
    "Question":str
}
'''
def hash_oral(ex_id,student_id):
    return ex_id + "-" + "student_id"

@api.route('/create_oral',methods=['POST'])
def oral():
    req = request.json

    h_ex = hash_oral(req["Ex_id"],req["Student_id"])

    topics = req["Topics"]
    question = req["Question"]

    val = [api_ai.gen_sys_oral(topics),api_ai.gen_ass_oral(question)]
    print(val)

    cache.set(h_ex,json.dumps(val),cache_timeout)

    return "201"#status.HTTP_201_CREATED

'''
{
    "Ex_id":str
    "Student_id":str
    "Answer":str 
}
'''

@api.route('/get_oral',methods=['GET'])
def get_oral():
    req = request.json

    h_ex = hash_oral(req["Ex_id"],req["Student_id"])
    prev = cache.get(h_ex)

    if prev:
        prev = json.loads(prev)
        answer = req["Answer"]
        answer = api_ai.gen_user_oral(answer)
        prev.append(answer)
        print(prev)

        resp = api_ai.send_oral(prev)
        prev.append({"role":"assistant","content":resp})

        cache.set(h_ex,json.dumps(prev),cache_timeout)

        ret = {"Question":resp}

    else:
        ret = "404"#status.HTTP_404_NOT_FOUND

    return ret

'''
{
   "Ex_id":str
   "Student_id":str
   "Topics":[str] 
}
'''

@api.route('/eval_oral',methods=['GET'])
def eval_oral():
    req = request.json

    h_ex = hash_oral(req["Ex_id"],req["Student_id"])
    prev = cache.get(h_ex)

    if prev:
        prev = json.loads(prev)
        topics = req["Topics"]
        resp = api_ai.send_eval_oral(topics,prev)

        ret = resp

    else:
        ret = "404"#status.HTTP_404_NOT_FOUND

    return ret

if __name__ == '__main__':
    api.run(host="0.0.0.0")