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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.Serializable;
import java.util.List;

@Path("/v1/unidadesfederativas")
@SuppressWarnings("serial")
public class UnidadeFederativaResource implements Serializable {

    private UnidadeFederativaRepository repository;

    @Inject
    public UnidadeFederativaResource( UnidadeFederativaRepository repository ){
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Recuperar todas unidades federativas." ,
        	description = "Recupera todas as unidades federativas cadastradas na base de dados."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Lista de todos registros recuperados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public List<UnidadeFederativa> getUnidadesFederativas(){
        List<UnidadeFederativa> resultado = this.repository.findAll();
        //System.out.println("Unidades Federativas size: " + resultado.size() );
        return resultado;
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Recuperar unidade federativa por identificador." ,
        	description = "Recupera uma unidade federativas cadastrada na base de dados através de seu identificador."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Unidade federativas com identificador selecionado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Unidade federativa não localizada com o identificador informado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    
    public UnidadeFederativa getUnidadeFederativaById( 
    		@Parameter(    				
    	            description = "Identificador da unidade federativa.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		  		
    		@PathParam("id") Integer id ) {
    	return this.repository.findById(id).orElseThrow( () -> new UnidadeFederativaNotFoundException(id));
    }

    @DELETE
    @Path("{id}")
    @Operation(
        	summary = "Excluir unidade federativa por identificador." ,
        	description = "Excluir uma unidade federativa cadastrada na base de dados através de seu identificador."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Unidade federativa excluída da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Unidade federativa não localizada com o identificador informado." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public void delete( 
    		@Parameter(    				
    	            description = "Identificador da unidade federativa.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		
    		@PathParam("id") Integer id ){
        this.repository.deleteById(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Cadastrar unidade federativas." ,
        	description = "Cadastrar uma unidade federativa na base de dados."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Unidade federativa cadastrado da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "400" , 
        				description = "Unidade federativa não pode ser cadastrada." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })    
    public UnidadeFederativa newUnidadeFederativa( UnidadeFederativa uf ) {
    	return this.repository.save( uf );
    }

    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Atualizar unidade federativa." ,
        	description = "Atualizar uma  unidade federativa na base de dados por meio identificador informado, caso identicador não exista sistem criará um novo registro."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Unidade federativa atualizada da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "400" , 
        				description = "Unidade federativa não pode ser atualizada." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public UnidadeFederativa replaceUnidadeFederativa( 
    		@Parameter(    				
    	            description = "Identificador da unidade federativa.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		    		
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        	summary = "Atualizar campos da unidade federativa." ,
        	description = "Atualizar campos do unidade federativa na base de dados por meio identificador informado, caso identicador não exista sistema informará erro."
        	
        )
        @APIResponses( value = {
        		@APIResponse( 
        				responseCode = "200" , 
        				description = "Campos da unidade federativa atualizado da base de dados." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )}) ,
        		@APIResponse( 
        				responseCode = "404" , 
        				description = "Unidade federativa não encontrada." , 
        				content = { @Content( mediaType = MediaType.APPLICATION_JSON )})
        })
    public UnidadeFederativa updateUnidadeFederativa( 
    		@Parameter(    				
    	            description = "Identificador da unidade federativa.",
    	            required = true,
    	            example = "1",
    	            schema = @Schema(type = SchemaType.INTEGER))   		 		
    		
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
