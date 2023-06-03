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