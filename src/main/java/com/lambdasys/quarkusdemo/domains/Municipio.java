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

@EqualsAndHashCode(of = {"id"})
@ToString(of={"id","nome","uf"})

@Entity
@Table(name="municipio")
@SuppressWarnings("serial")
@XmlRootElement(name = "municipio")
public class Municipio implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @NotBlank @NotEmpty
    @Size(min = 1 , max = 45)
    @Column(name="nome")
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidade_federativa_id" , referencedColumnName = "id")
    private UnidadeFederativa uf;

}
