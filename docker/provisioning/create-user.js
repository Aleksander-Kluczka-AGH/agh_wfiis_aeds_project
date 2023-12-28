db.createUser({
    "user": "users_local",
    "pwd": "users_local",
    "roles": [
        {
            "role": "readWrite",
            "db": "users_local"
        }
    ]
});