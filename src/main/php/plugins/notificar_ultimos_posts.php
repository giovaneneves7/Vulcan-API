<?php

    /**
     * Plugin Name: Notificar novos posts
     * Plugin URI: https://github.com/NeveScript/Vulcan-API
     * Description: Faz requisição ao endpoint que envia um webhook com as informações dos novos posts.
     * Version: 1.0.0
     * Author: NekoYasha
     * Author URI: https://github.com/NeveScript
     * Text Domain: notificar-novos-posts
     */
    function enviar_post_recente_para_endpoint()
    {

        if(is_admin())
        {

            $args = array(
                'posts_per_page' => 1, // Pegar só post mais recente
            );
            $posts_recentes = get_posts($args);

            if ($posts_recentes)
            {
                $post = $posts_recentes[0];

                // Montar os dados do post para enviar no corpo da requisição
                $info_post = array(
                    'nome_novel' => $post->post_title,
                    'titulo_postagem' => $post->post_title,
                    'autor_postagem' => $post->post_author,
                    'link_postagem' => get_permalink($post),
                    'link_avatar_autor' => get_avatar_url($post->post_author),
                );

                // Configurar as opções da requisição
                $configs_requisicao = array(
                    'method' => 'POST',
                    'body' => json_encode($info_post),
                    'headers' => array(
                        'Content-Type' => 'application/json',
                    ),
                );

                // Fazer a requisição para o endpoint
                $response = wp_remote_request('MEU_ENDPOINT', $configs_requisicao);

                // Verificar se a requisição foi bem-sucedida
                if (is_wp_error($response)) {
                    error_log('Erro na requisição: ' . $response->get_error_message());
                } else {
                    $response_code = wp_remote_retrieve_response_code($response);
                    if ($response_code === 200) {
                        // Requisição bem-sucedida
                        error_log('Requisição enviada com sucesso!');
                    } else {
                        // Requisição retornou um código de erro
                        error_log('Erro na requisição. Código de resposta: ' . $response_code);
                    }
                }
            }
        }

    }
        // Agendar a execução da função enviar_post_recente_para_endpoint
        add_action('publish_post', 'enviar_post_recente_para_endpoint');


?>