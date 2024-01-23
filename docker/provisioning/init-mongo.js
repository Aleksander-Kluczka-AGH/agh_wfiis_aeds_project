db.createUser({
    "user": "user",
    "pwd": "password",
    "roles": [
        {
            "role": "readWrite",
            "db": "room_info_db"
        }
    ]
});

