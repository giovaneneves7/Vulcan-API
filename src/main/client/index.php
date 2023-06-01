<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=
    , initial-scale=1.0">

    <link rel="stylesheet" type="text/css"href="css/style.css">
    <!-- Bootstrap import-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <title>JVulcan API</title>

</head>
<body class="bg-dark">

    <header id="cabecalho">

        <?php
            include_once('php/request.php');

            $cor_botao = 'success';
            $texto_botao = 'Login';

            if(checar_status() === 'inativa')
            {

                $cor_botao = 'warning';
                $texto_botao = 'Impossível Fazer Login';

            }

            echo '<button class="col-2 btn btn-' . $cor_botao . '" id="btn-login">' . $texto_botao . '</button>';

        ?>
        

        <div class="logo-container">

            <img src="resources/images/vulcan_logo_azul.png" alt="Logo da Vulcan" />

        </div>

    </header>
    <hr class="bg-light" style="opacity: 60%">

    <div class="info text-light" style="margin: 2% 42%">

        <h2>JVulcan API</h2>
        <p>v1.0.0.1-beta</p>
        <p>Status: em construção</p>
    </div>
    
</body>
</html>