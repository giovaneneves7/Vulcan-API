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
    <form action="#" class="form">

    <!-- Nome da novel --->
    <div class="col-8">

        <label for="nome-novel">Nome da Novel</label>
        <input type="text" name="nomeNovel" placeholder="Nome da Novel" id="nome-novel" class="form-control mb-3">
    

    </div>

    <!-- Autor ou tradutor da novel --->
    <div class="col-8">


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
    <div class="col-12" id="adicionar-staff">
    
        <label for="input-nome-staff">Adicionar Autor ou Tradutor</label>
        <input type="text" id="input-nome-staff" class="form-control mb-3 col-3" placeholder="Nome do autor ou tradutor">

    </div>
    
    <div class="views d-flex">
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
    <div class="col-6">
    
        <label for="slug">Slug da Novel</label>
        <input type="text" oninput="pegarIndice()" class="form-control" name="slug" id="slug" placeholder="sdn-slug-da-novel">

    </div>

    <!-- Indice da novel --->
    <div class="col-6">
    
        <label for="slug">Indice da Novel</label>
        <input type="text" class="form-control" name="indice" id="indice" placeholder="indice da novel">

    </div>

    <!-- Nacionalidade da novel --->
    <div class="col-6">

        <label for="nacionalidade-check">Nacionalidade</label>
        <div class="form-check" id="nacionalidade-check">
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

     <!-- Nacionalidade da novel --->
    <div class="col-6">
        
        <label for="check-status">Satus</label>
        <div class="form-check" id="check-status">

        <div class="form-check">
            <input type="radio" id="check-ativo" class="form-check-input" name="status" value="ativo">
            <label class="form-check-label">Ativo</label>
        </div>
        <div class="form-check">
            <input type="radio" id="check-completo" class="form-check-input" name="status" value="completo">
            <label class="form-check-label">Completo</label>
        </div>
        <div class="form-check">
            <input type="radio" id="check-pausado" class="form-check-input" name="status" value="pausado">
            <label class="form-check-label">Pausado</label>
        </div>
        <div class="form-check">
            <input type="radio" id="check-dropado" class="form-check-input" name="status" value="dropado">
            <label class="form-check-label">Dropado</label>
        </div>

        </div>
    </div>
    

    <input type="submit" class="btn btn-outline-primary" value="Cadastrar">

    </form>
    <!-- Fim do form --->

    <script src="../../js/form.js">

        ocultarInputAdiconarMembro();
        pegarIndice();

    </script>
</body>
</html>