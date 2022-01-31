package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.dao.ProjetoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("projetos")
public class ProjetoResource {

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") Long id) {
        Projeto projeto = new ProjetoDAO().busca(id);
        return projeto.toXML();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(String conteudo) throws URISyntaxException {
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        new ProjetoDAO().adiciona(projeto);
        URI uri = new URI("/projetos/" + projeto.getId());
        return Response.created(uri).build();
    }

    @Path("remove/{id}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id) {
        new ProjetoDAO().remove(id);
        return Response.ok().build();

    }

}
