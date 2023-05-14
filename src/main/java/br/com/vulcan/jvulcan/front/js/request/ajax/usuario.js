
function cadastrarUsuario() {

    $(document).ready(
        function () {
            $('form').submit(
                function (event) {
                    event.preventDefault();

                    var idDiscord = $('#idDiscord').val();
                    var nomeUsuario = $('#nomeUsuario').val();
                    var senha = $('#senha').val();

                    var host = "http://localhost:8080"

                    $.ajax({
                        type: "POST",
                        url: host.concat("/nekoyasha7/api/jvulcan-v1.000/usuarios/usuario"),
                        data: {
                            id_usuario: idDiscord,
                            nome: nomeUsuario,
                            senha: senha
                        },
                        success: function (response) {
                            console.log(response);
                        },
                        error: function (xhr) {
                            console.log(xhr.responseText);
                        }
                    });
                }
            );
        }
    );

}
