# Eventos
Aplicação de gestão de eventos

Para executar a solução em container usando docker swarm:


Clone da aplicação
````
    git clone https://github.com/hodestito/Eventos.git
    cd Eventos
````

Criação do swarm
````
    docker swarm init
````

Criação das redes overlay
````
    docker network create -d overlay usuarios_network
    docker network create -d overlay eventos_network
    docker network create -d overlay vendas_network
````

Deploy das aplicações
````
    docker stack deploy -c docker-compose.yml Eventos
````
    
