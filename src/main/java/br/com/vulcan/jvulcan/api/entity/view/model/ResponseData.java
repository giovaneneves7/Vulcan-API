package br.com.vulcan.jvulcan.api.entity.view.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@lombok.Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Data {

    String category;
    Long totalViews;

}
