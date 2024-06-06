Para registrarse e iniciar sesión:

http://localhost:8080/api/auth/signup

{\
    "username": name,\
    "email": email,\
    "password": password\
}

http://localhost:8080/api/auth/signin

{\
    "username": username,\
    "password": password\
}

Colocar el token obtenido en el header para usar el resto de la aplicación, dura 60 segundos

Authorization:Bearer token