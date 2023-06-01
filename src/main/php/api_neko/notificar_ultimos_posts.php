<?php

    require_once('config.php');

    $ultimo_post_notificado = "";
    function enviar_post_recente_para_endpoint()
    {

        global $ultimo_post_notificado;

        if(is_admin())
        {

            $args = array(
                'posts_per_page' => 1, // Pegar só post mais recente
            );

            $posts_recentes = get_posts($args);

            if ($posts_recentes)
            {

                $post = $posts_recentes[0];
                $post_id = $post->ID;

                //--+ Pega a categoria da postagem +--//
                $categorias = get_the_category($post_id);
                $categoria_post = "";

                //--+ Se houver categoria, pega a primeira +--//
                if($categorias)
                {
                    $categoria_post = $categorias[0]->name;
                }

                //--+ Pega o nome do autor do post +--//
                $autor_nome = get_the_author($post_id);

                if($post_id != $ultimo_post_notificado)
                {

                    // Montar os dados do post para enviar no corpo da requisição
                    $info_post = array(

                        'nome_novel' => $post->post_title,
                        'titulo_postagem' => $post->post_title,
                        'autor_postagem' => $autor_nome,
                        'link_postagem' => get_permalink($post),
                        'link_avatar_autor' => get_avatar_url($post->post_author),
                        'categoria' => $categoria_post,

                    );

                    // Configurar as opções da requisição
                    $configs_requisicao = array(
                        'method' => 'POST',
                        'body' => json_encode($info_post),
                        'headers' => array(
                            'Content-Type' => 'application/json',
                            'Api-Key' => API_KEY,
                        ),
                    );

                    // Fazer a requisição para o endpoint
                    $host = 'https://meu_host';
                    $url = '/nekoyasha7/jvulcan-api/v1/posts/post';
                    $response = wp_remote_request($host . $url, $configs_requisicao);

                    // Verificar se a requisição foi bem-sucedida
                    if (is_wp_error($response))
                    {
                    
                        error_log('Erro na requisição: ' . $response->get_error_message());
                    
                    } else 
                    {
                    
                        $response_code = wp_remote_retrieve_response_code($response);
                        if ($response_code === 200) 
                        {
                            // Requisição bem-sucedida
                            error_log('Requisição enviada com sucesso!');
                        } else 
                        {
                            // Requisição retornou um código de erro
                            error_log('Erro na requisição. Código de resposta: ' . $response_code);
                        }
                    }

                    $ultimo_post_notificado = $post_id;
                }

            }
        }

    }

    

?>