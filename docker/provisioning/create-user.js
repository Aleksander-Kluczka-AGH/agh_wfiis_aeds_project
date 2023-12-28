db.createUser({
    "user": "reservations_local",
    "pwd": "reservations_local",
    "roles": [
        {
            "role": "readWrite",
            "db": "reservations_local"
        }
    ]
});