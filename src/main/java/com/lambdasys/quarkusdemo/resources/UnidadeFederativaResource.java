package com.lambdasys.quarkusdemo.resources;

import com.lambdasys.quarkusdemo.domains.UnidadeFederativa;
import com.lambdasys.quarkusdemo.exceptions.UnidadeFederativaNotFoundException;
import com.lambdasys.quarkusdemo.repositories.UnidadeFederativaRepository;

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

@Path("/v1/unidadesfederativas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SuppressWarnings("serial")
public class UnidadeFederativaResource implements Serializable {

    private UnidadeFederativaRepository repository;

    @Inject
    public UnidadeFederativaResource( UnidadeFederativaRepository repository ){
        this.repository = repository;
    }

    @GET
    @Produces
    public List<UnidadeFederativa> getUnidadesFederativas(){
        return this.repository.findAll();
    }
    
    @GET
    @Path("{id}")
    @Produces
    public UnidadeFederativa getUnidadeFederativaById( @PathParam("id") Integer id ) {
    	return this.repository.findById(id).orElseThrow( () -> new UnidadeFederativaNotFoundException(id));
    }

    @DELETE
    @Path("{id}")
    public void delete( @PathParam("id") Integer id ){
        this.repository.deleteById(id);
    }

    @POST
    @Produces
    @Consumes
    public UnidadeFederativa newUnidadeFederativa( UnidadeFederativa uf ) {
    	return this.repository.save( uf );
    }

    @PUT
    @Path("{id}")
    @Produces
    @Consumes
    public UnidadeFederativa replaceUnidadeFederativa( 
    		@PathParam("id") Integer id , 
    		UnidadeFederativa newUnidadeFederativa ) {

        UnidadeFederativa resultado = repository.findById( id )
                .map( ( unidadeFederativa ) -> {
                   unidadeFederativa.setNome( newUnidadeFederativa.getNome() );
                   unidadeFederativa.setSigla( newUnidadeFederativa.getSigla() );
                   return repository.save( unidadeFederativa );
                })
                .orElseGet( () -> { return repository.save( newUnidadeFederativa ); }) ;
       
        return resultado;
    	
    }
    
    @PATCH
    @Path("{id}")
    @Produces
    @Consumes
    public UnidadeFederativa updateUnidadeFederativa( 
    		@PathParam("id") Integer id , 
    		UnidadeFederativa newUnidadeFederativa ) {

        UnidadeFederativa resultado = repository.findById( id )
                .map( unidadeFederativa -> {

                    boolean patched = false;

                    if( StringUtils.isNotBlank( newUnidadeFederativa.getNome() ) ){
                        unidadeFederativa.setNome( newUnidadeFederativa.getNome() );
                        patched = true;
                    }

                    if( StringUtils.isNotBlank( newUnidadeFederativa.getSigla() ) ) {
                        unidadeFederativa.setSigla(newUnidadeFederativa.getSigla());
                        patched = true;
                    }

                    if( patched ) {
                        return this.repository.save(unidadeFederativa);
                    }else{
                        return unidadeFederativa;
                    }

                }).orElseThrow( () -> new UnidadeFederativaNotFoundException( id ) );
       
        return resultado;
    	
    }
    
    
}
