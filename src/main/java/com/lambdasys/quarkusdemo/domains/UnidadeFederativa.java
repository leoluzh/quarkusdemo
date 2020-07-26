package com.lambdasys.quarkusdemo.domains;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

/**
 * leoluzh
 * @since 25/07/2020
 * @version 0.1
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@ToString(of = {"id","sigla","nome"})
@EqualsAndHashCode(of={"id"})

@Entity
@Table(name = "unidade_federativa")
@SuppressWarnings("serial")
@XmlRootElement(name = "unidadefederativa")
public class UnidadeFederativa implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @NotEmpty @NotBlank
    @Size(min = 1 , max= 2)
    @Column(name="sigla")
    private String sigla;

    @NotNull @NotEmpty @NotBlank
    @Size(min = 1 , max = 30)
    @Column(name="nome")
    private String nome;

}
