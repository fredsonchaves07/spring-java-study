function consultarRestaurante {
    $.ajax({
        url: "localhost:8080/restaurantes",
        type: "get",
        success: function(response) {
            $("#conteudo").text(JSON.stringfy(response));
        }
    });
}

$("#botao").click(consultarRestaurante);