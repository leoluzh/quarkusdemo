package com.lambdasys.quarkusdemo.graphql;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import com.lambdasys.quarkusdemo.domains.UnidadeFederativa;
import com.lambdasys.quarkusdemo.exceptions.UnidadeFederativaNotFoundException;
import com.lambdasys.quarkusdemo.repositories.UnidadeFederativaRepository;

@GraphQLApi
@SuppressWarnings("serial")
public class UnidadeFederativaResource implements Serializable {

    // ATENCAO: Quarkus nao aceita injecao de dependencia sobre propriedades privadas/protegidas.
    private UnidadeFederativaRepository repository;
    
    @Inject
    public UnidadeFederativaResource( UnidadeFederativaRepository repository ) {
    	this.repository = repository;
	}

    @Query("unidadesfederativas")
    @Description("Buscar todos os unidades federativas disponíveis.")
    public List<UnidadeFederativa> getUnidadesFederativas() {
        var resultado = this.repository.findAll();
        return resultado;
    }

    @Query("unidadefederativa")
    @Description("Buscar municipio por id.")
    public UnidadeFederativa getUnidadeFederativa( @Name("unidadeFederativaId") int unidadeFederativaId ) {
        var resultado = this.repository.findById( unidadeFederativaId ).orElseThrow( () -> new UnidadeFederativaNotFoundException( unidadeFederativaId ) );
        return resultado;
    }


}
