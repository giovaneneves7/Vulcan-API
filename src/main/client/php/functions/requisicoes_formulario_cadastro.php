<?php

    /**
    * Exibe a lista de autores no banco de dados. 
    */
    function listar_membros_no_select()
    {

        $host = "https://spring-api.thankfulriver-9e15c811.eastus.azurecontainerapps.io";
        $url = "/nekoyasha7/jvulcan-api/v1/novels";

        $res = file_get_contents($host . $url);

        if($res !== false){

            $dados = json_decode($res, true);

            if($dados !== null)
            {
                    foreach($dados as $novel)
                    {
                        $membros_adicionados[] = array();
                        $autor_ou_tradutor = $novel['autor_ou_tradutor'];

                        if(!in_array($autor_ou_tradutor, $membros_adicionados))
                        {
                            $membros_adicionados[] = $autor_ou_tradutor;
                            $value = $novel['autor_ou_tradutor'];
                            echo '<option value="' . $value . '">' . $autor_ou_tradutor . '</option>';
                        }
                    }
            }

            echo '<option value="error"> Lista vazia </option>';

        } else{
            echo '<option value="error">Erro de conexão</option>';
        }

    }
        

        ?>