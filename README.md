# IST411-Using-JSON-with-REST

Upon run, there will be one board stored in memory. 

Currently, the boards do not persist between runs. This is because, in context, this api would be working with a database in order to maintain board information.

To GET the current list of boards, make a GET request to localhost:8000/boards

To GET a specific board, make a GET request to localhost:8000/boards?title=<NAME_OF_BOARD>

To POST a new board, make a POST request to localhost:8000/boards using the following JSON format.

     {"title":"BoardTest","description":"This is a test board."}
