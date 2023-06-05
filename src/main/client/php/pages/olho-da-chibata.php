<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap import-->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <title>Olho da Chibata</title>
</head>
<body>

    <h2>Olho da Chibata</h2>

    <table class="table">
        <thead class="table-dark">

            <tr>
                <th>Id</th>
                <th>Staff</th>
                <th>Novel</th>
                <th>Última Postagem</th>
                <th>Nick Discord</th>
                <th>Ação</th>
            </tr>

        </thead>
        <tbody>

            <tr>
                <?php

                    $host = "https://spring-api.thankfulriver-9e15c811.eastus.azurecontainerapps.io";
                    $url = "/nekoyasha7/jvulcan-api/v1/olho-da-chibata/membros/cobranca";
            
                    $curl = curl_init();
            
                    curl_setopt($curl, CURLOPT_URL, $host . $url);
                    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
            
                    $res = curl_exec($curl);
            
                    curl_close($curl);

                    if($res !== false){

                        $dados = json_decode($res, true);
            
                        if($dados !== null)
                        {
                                foreach($dados as $dado)
                                {
                                    echo '<td>'. $dado['id'] .  '</td>';
                                    echo '<td>'. $dado['nome_novel'] .  '</td>';
                                    echo '<td>'. $dado['autor_ou_tradutor'] .  '</td>';
                                }
                        }
            
                        echo '<option value="error"> Lista vazia </option>';
            
                    } else{
                        echo '<option value="error">Erro de conexão</option>';
                    }

                ?>
            </tr>

        </tbody>
    </table>
</body>
</html>