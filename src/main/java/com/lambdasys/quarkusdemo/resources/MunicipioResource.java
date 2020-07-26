package com.lambdasys.quarkusdemo.resources;

import com.lambdasys.quarkusdemo.domains.Municipio;
import com.lambdasys.quarkusdemo.exceptions.MunicipioNotFoundException;
import com.lambdasys.quarkusdemo.repositories.MunicipioRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Path("/v1/municipios")
@SuppressWarnings("serial")
public class MunicipioResource implements Serializable {

    private MunicipioRepository repository;

    @Inject
    public MunicipioResource( MunicipioRepository repository ){
        this.repository = repository;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Municipio> getMunicipios(){
    	List<Municipio> resultado = this.repository.findAll();
    	//System.out.println("Municipios size: " + resultado.size() );
    	return resultado;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Municipio getMunicipioById( @PathParam("id") Integer id ) {
    	return this.repository.findById( id ).orElseThrow( () -> new MunicipioNotFoundException( id ) );
    }
    
    @DELETE
    @Path("{id}")
    public void deleteMunicipio( @PathParam("id") Integer id ) {
    	this.repository.deleteById( id );
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Municipio newMunicipio( Municipio municipio ) {
    	return this.repository.save( municipio );
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Municipio replaceMunicipio( @PathParam("id") Integer id , Municipio newMunicipio ) {
    	
        final Municipio resultado = repository.findById( id ).map( municipio -> {
            municipio.setNome( newMunicipio.getNome() );
            municipio.setUf( newMunicipio.getUf() );
            return this.repository.save( municipio );
         }).orElseGet( () -> {
             return this.repository.save( newMunicipio );
         });
    	
        return resultado;
    	
    }
    
    @PATCH
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Municipio patchMunicipio( @PathParam("id") Integer id , Municipio newMunicipio ) {
    	
        final Municipio resultado = repository.findById( id ).map( municipio -> {
            boolean patch = false;
            if(StringUtils.isNotBlank( newMunicipio.getNome() ) ) {
                municipio.setNome(newMunicipio.getNome());
                patch = true;
            }
            if(Objects.nonNull(newMunicipio.getUf()) ) {
                municipio.setUf(newMunicipio.getUf());
                patch = true;
            }
            if( patch ) {
                return this.repository.save(municipio);
            }else{
                return municipio;
            }
        }).orElseThrow( () -> new MunicipioNotFoundException( id ) );
    	
        return resultado;
    	
    }
        
}
