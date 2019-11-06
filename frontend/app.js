//jshint esversion:6

const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const request = require("request");

const app = express();



app.use(cors());
app.options('*', cors());
app.use(express.static("public"));
app.use(bodyParser.urlencoded({extended: true}));


app.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*');
  next();
});

app.listen(process.env.PORT || 3000,function(){
  console.log("Listen on port 3000.");
});

app.get("/", function(req, res){
  res.sendFile(__dirname + "/signup.html");
});

app.post("/", function(req, res){
  var firstName = req.body.fName;
  var phone = req.body.lPhone;
  var email = req.body.email;
  var senha = req.body.senha;

  var data = {
        nome: firstName,
        telefone: phone,
        email: email,
        senha: senha
  };

  var jsonData = JSON.stringify(data);

  console.log(jsonData)

  var options = {
    url : "http://usuarios-app:8080/usuarios",
    method : "POST",
    headers: {
                "Content-Type": "application/json"},
    body : jsonData
  };

  request(options, function(error, response, body){
    console.log(response.statusCode)
    if(error){
      res.sendFile(__dirname + "/failure.html");
    }else{
      if(response.statusCode === 200){
        res.sendFile(__dirname + "/success.html");
      }else{
        res.sendFile(__dirname + "/failure.html");
      }
    }
  });
});

app.get("/signin", function(req, res){
  res.sendFile(__dirname + "/signin.html");
});

app.post("/signin", function(req, res){
  var email = req.body.email;
  var senha = req.body.senha;
  var nome = "";
  var telefone = "";

  var data = {
        email: email,
        senha: senha,
        telefone: telefone,
        nome: nome
  };

  var jsonData = JSON.stringify(data);

  console.log(jsonData)

  var options = {
    url : "http://usuarios-app:8080/usuarios/autenticacao",
    method : "POST",
    headers: {
                "Content-Type": "application/json"},
    body : jsonData
  };

  request(options, function(error, response, body){
    console.log(response);
    console.log(error);
    console.log(body);
    if(error){
      res.sendFile(__dirname + "/failure.html");
    }else{
      if(response.statusCode === 200){
        res.sendFile(__dirname + "/home.html");
      }else{
        res.sendFile(__dirname + "/failure.html");
      }
    }
  });
});

app.post("/failure", function(req, res){
  res.redirect("/signin");
});


app.get("/usuarios", function(req, res){

  var options = {
    url : "http://localhost:8080/usuarios",
    method : "GET",
    headers: {"Content-Type": "application/json"}
  };

  request(options, function(error, response, body){
    res.setHeader("Access-Control-Allow-Origin", "*");
    if(error){
      res.sendFile(__dirname + "/failure.html");
    }else{
      if(response.statusCode === 200){
        res.send(body);
      }else{
        res.sendFile(__dirname + "/failure.html");
      }
    }
  });
}

);
