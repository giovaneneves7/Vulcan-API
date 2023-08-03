package br.com.vulcan.jvulcan.api.entity.novel.model.dto.request;

import br.com.vulcan.jvulcan.api.entity.novel.model.Novel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastrarNovelDto(

        @JsonProperty(value = "titulo")
        @NotNull(message = "O título da novel não pode ser nulo")
        @NotBlank
        String titulo,

        @JsonProperty(value = "capa")
        @NotNull(message = "a capa da novel não pode ser nulo")
        @NotBlank
        String capa,

        @JsonProperty(value = "nacionalidade")
        @NotNull(message = "a nacionalidade da novel não pode ser nulo")
        @NotBlank
        String nacionalidade,

        @JsonProperty(value = "tipo")
        @NotNull(message = "o tipo da novel não pode ser nulo")
        @NotBlank
        String tipo,

        @JsonProperty(value = "autor")
        @NotNull(message = "o autor não pode ser nulo")
        @NotBlank
        String staff,

        @JsonProperty(value = "status")
        @NotNull(message = "o campo de status não pode ser nulo")
        @NotBlank
        String status,

        @JsonProperty(value = "autor")
        @NotNull(message = "O nome de autor original não pode ser nulo")
        @NotBlank
        String autor,

        @JsonProperty(value = "slug")
        @NotNull(message = "o slug não pode ser nulo")
        @NotBlank
        String slug
        ) {

        /**
         * Converte o DTO para objeto 'Novel'
         *
         * @return um objeto 'Novel' com os dados do DTO.
         */
        public Novel converter(){

                Novel novel = new Novel();

                novel.setNome(this.titulo);
                novel.setCapa(this.capa);
                novel.setNacionalidade(this.nacionalidade);
                novel.setCargo(this.tipo);
                novel.setStatus(this.status);
                novel.setAutor(this.staff);
                novel.setEscritor(this.autor);
                novel.setSlug(this.slug);

                return novel;
        }
}
