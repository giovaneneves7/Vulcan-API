<?php

    /**
     * Plugin Name: Api Neko
     * Plugin URI: https://github.com/NeveScript/Vulcan-API
     * Description: Api para interação com a api construida pelo Neko.
     * Version: 1.0.1
     * Author: NekoYasha
     * Author URI: https://github.com/NeveScript
     * Text Domain: api-neko
     */

    include_once('notificar_ultimos_posts.php');
    
    enviar_post_recente_para_endpoint();
    add_action('publish_post', 'enviar_post_recente_para_endpoint');

?>