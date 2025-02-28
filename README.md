docker use curl : curl --location 'http://localhost:8081/api/qna/ask' \
--header 'Content-Type: application/json' \
--data '{
    "question": "what ?"
}
'without docker :curl --location 'http://localhost:8080/api/qna/ask' \
--header 'Content-Type: application/json' \
--data '{
    "question": "what ?"
}
'
