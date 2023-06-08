<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap import-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <title>Cadastrar Novel</title>
</head>
<body class="container pt-10 bg-dark text-light">
    
    <div style="text-align: center">
        <h2 class="text-light mt-3">Cadastrar Novel</h2>
    </div>
    
    <hr class="bg-light">

    <!-- Formulário de cadastro de novel-->
    <form id="novel-form" class="form" method="post">

    <!-- Nome da novel --->
    <div class="col-8 mb-3">

        <label for="nome-novel">Nome da Novel</label>
        <input type="text" name="nome_projeto" placeholder="Nome da Novel" id="nome-novel" class="form-control mb-3">
    

    </div>

    <!-- Autor ou tradutor da novel --->
    <div class="col-8 mb-3">


        <label for="selecionar-staff">Autor ou tradutor</label>
        <select class="form-control mb-3" id="selecionar-staff" onchange="ocultarInputAdiconarMembro();">

            <?php

                require_once('../functions/requisicoes_formulario_cadastro.php');
                
                listar_membros_no_select();

                echo '<option value="adicionar" selected>Adicionar outro</option>';
            ?>

        </select>
    </div>

    <!-- Adicionar autor ou tradutor à novel da novel --->
    <div class="col-12 mb-3" id="adicionar-staff">
    
        <label for="input-nome-staff">Adicionar Autor ou Tradutor</label>
        <input type="text" id="input-nome-staff" class="form-control mb-3 col-3" placeholder="Nome do autor ou tradutor">


        <label for="cargo-staff">Cargo</label>
        <input type="text" id="cargo-staff" class="form-control mb-3 col-3" placeholder="Cargo">

    </div>
    
    <div class="views d-flex mb-3">
        <div class="col-5">

            <label for="views-totais">Views Totais</label>
            <input type="text" class="form-control" name="views-totais" id="views-totais" placeholder="">
            
        </div>
        
        <div class="col-5">

            <label for="views-mensais">Views Mensais</label>
            <input type="text" class="form-control" name="views-mensais" id="views-mensais">

        </div>
        
    </div>

    <!-- Slug da novel --->
    <div class="col-6 mb-3">
    
        <label for="slug">Slug da Novel</label>
        <input type="text" oninput="pegarIndice()" class="form-control" name="slug" id="slug" placeholder="sdn-slug-da-novel">

    </div>

    <!-- Indice da novel --->
    <div class="col-6 mb-3">
    
        <label for="slug">Indice da Novel</label>
        <input disabled type="text" class="form-control" name="indice" id="indice" placeholder="indice da novel">

    </div>

    <!-- Escritor da novel --->
    <div class="col-6">
    
        <label for="escritor">Escritor Original da Novel</label>
        <input type="text" class="form-control" name="escritor" id="escritor" placeholder="escritor da novel">

    </div>

    <!-- Total de capítulos da nvel --->
    <div class="col-6 mb-3">
    
        <label for="quantidade">Quantidade de Capítulos</label>
        <input type="text" class="form-control" name="quantidade" id="quantidade" placeholder="quantidade de caps">

    </div>
    
    <div class="col-6 mb-3">
    
        <label for="quantidade">Id do cargo da novel</label>
        <input type="text" class="form-control" name="id_cargo" id="cargo" placeholder="cargo do discord da novel">
    </div>
</div>

    
    <div class="col-6 mb-3">
    
        <label for="quantidade">Link da Capa</label>
        <input type="text" class="form-control" name="capa" id="capa" placeholder="Link da capa da novel">
    
    </div>


    <!-- Nacionalidade da novel --->
    <div class="col-6">

        <label for="nacionalidade-check">Nacionalidade</label>
        <div class="form-check" id="nacionalidade-check">
                
            <div class="form-check">
                <input type="radio" id="check-oci" class="form-check-input" name="nacionalidade" value="oci">
                <label class="form-check-label" for="check-oci">Ocidental</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-br" class="form-check-input" name="nacionalidade" value="br">
                <label class="form-check-label">Brasileira</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-jp" class="form-check-input" name="nacionalidade" value="jp">
                <label class="form-check-label">Japonesa</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-ch" class="form-check-input" name="nacionalidade" value="ch">
                <label class="form-check-label">Chinesa</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-co" class="form-check-input" name="nacionalidade" value="co">
                <label class="form-check-label">Coreana</label>
            </div>
        </div>

    </div>

     <!-- Nacionalidade da novel --->
    <div class="col-6">
        
        <label for="check-status">Satus</label>
            <div class="form-check" id="check-status">

            <div class="form-check">
                <input type="radio" id="check-ativo" class="form-check-input" name="status_projeto" value="em breve">
                <label class="form-check-label">Em breve</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-ativo" class="form-check-input" name="status_projeto" value="ativo">
                <label class="form-check-label">Ativo</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-completo" class="form-check-input" name="status_projeto" value="completo">
                <label class="form-check-label">Completo</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-pausado" class="form-check-input" name="status_projeto" value="pausado">
                <label class="form-check-label">Pausado</label>
            </div>
            <div class="form-check">
                <input type="radio" id="check-dropado" class="form-check-input" name="status_projeto" value="dropado">
                <label class="form-check-label">Dropado</label>
            </div>

        </div>
    </div>
    

    <div class="d-grid gap-2 col-6 mx-auto">
        <input type="submit" class="btn mb-3 btn-outline-primary" onclick="enviarFormulario()" value="Cadastrar">
    </div>

    <div id="alert-empty-field" class="bg-warning text-dark" style="display: none; border-radius: 15px">

        <p style="font-size: 20">Todos os campos devem ser preenchidos!</p>

    </div>
    <div id="success-registration" class="bg-success text-light" style="display: none; border-radius: 15px">

        <p class="ml-10" style="font-size: 20">Novel cadastrada com sucesso!</p>

    </div>

    <div id="failure-registration" class="bg-success text-light" style="display: none; border-radius: 15px">

        <p class="ml-10" style="font-size: 20">Ocorreu um erro do lado do servidor ao tentar cadastrar a novel!</p>

    </div>

    </form>
    <!-- Fim do form --->

    <script src="../../js/form.js">

        ocultarInputAdiconarMembro();
        pegarIndice();
        enviarFormulario();

    </script>
</body>
</html>