<?php

function checar_status() : string
{

    require_once('config.php');

    $host = 'http://localhost:8080';
    $url = '/nekoyasha7/jvulcan-api/v1/status';

    $configs = array(
        
        'http' => array(
            'method' => 'GET',
            'header' => array(
                                'Content-Type' => 'application/json',
                                'api_key' => API_KEY,
                            ),
        
        )
    );

    try {

        $contexto = stream_context_create($configs);
        $resposta = @file_get_contents($host . $url, false, $contexto);

        if ($resposta === false) {
            return 'inativa';
        } else {
            return 'ativa';
        }
    } catch (Exception $e) {
        return 'inativa';
    }
}


?>