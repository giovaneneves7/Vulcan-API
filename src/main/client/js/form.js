function ocultarInputAdiconarMembro() {
    var select = document.getElementById('selecionar-staff');
    var elementoSelecionado = select.options[select.selectedIndex].value;

    if (elementoSelecionado == 'adicionar') {
        document.getElementById('adicionar-staff').style.display = 'block';
    } else {
        document.getElementById('adicionar-staff').style.display = 'none';
    }
}

function pegarIndice() {
    var slug = document.getElementById('slug').value;
    document.getElementById('indice').value = slug;

}

function enviarFormulario() {

    event.preventDefault();

    var formulario = document.getElementById('novel-form');
    var dados = {};

    try {

        dados.nome_projeto = formulario.querySelector('[name="nome_projeto"]').value;
        dados.views_totais = formulario.querySelector('[name="views-totais"]').value;
        dados.views_mensais = formulario.querySelector('[name="views-mensais"]').value;
        dados.slug = formulario.querySelector('[name="slug"]').value;
        dados.indice = formulario.querySelector('[name="indice"]').value;
        dados.escritor = formulario.querySelector('[name="escritor"]').value;
        dados.quantidade = formulario.querySelector('[name="quantidade"]').value;
        dados.id_cargo = formulario.querySelector('[name="id_cargo"]').value;
        dados.capa = formulario.querySelector('[name="capa"]').value;
        dados.nacionalidade = formulario.querySelector('[name="nacionalidade"]:checked').value;
        dados.status_projeto = formulario.querySelector('[name="status_projeto"]:checked').value;

        document.getElementById('alert-empty-field').style.display = 'none';

        fazerRequisicao(dados);

    } catch (error) {

        exibirAvisoCamposVazios();
    }

    return;

}

function exibirAvisoCamposVazios() {

    document.getElementById('alert-empty-field').style.display = 'block';

}

function fazerRequisicao(dados) {

    var host = "https://1a34-45-181-199-212.ngrok-free.app";
    var url = "/nekoyasha7/jvulcan-api/v1/novels/novel";

    fetch(host.concat(url), {
        method: "POST",
        headers: {
            "Application-Content": "application/json"
        },
        body: JSON.stringify(dados)
    }
    ).then(function (response) {

        if (response.ok) {

            document.getElementById('success-registration').style.display = "block";
            document.getElementById('failure-registration').style.display = "none";


        } else {

            document.getElementById('failure-registration').style.display = "block";
            document.getElementById('success-registration').style.display = "none";
            throw new Error("Erro na requisição");

        }

    }).then(function (resposta) {

        console.log(resposta);

    }).catch(function (error) {

        document.getElementById('failure-registration').style.display = "block";
        document.getElementById('success-registration').style.display = "none";
        console.error(error);

    });

}