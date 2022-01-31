package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteTest {

    private HttpServer server;
    private Client client;
    private WebTarget target;

    @Before
    public void iniciaServidor() {
        this.server = Servidor.getHttpServer();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new LoggingFilter());
        this.client = ClientBuilder.newClient(clientConfig);
        this.target = this.client.target("http://localhost:8080");
    }

    @Test
    public void testaRotaProjeto() {

        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML_TYPE);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("Location");
        String conteudo = client.target(location).request().get(String.class);

        Assert.assertTrue(conteudo.contains("Tablet"));


    }

    @Test
    public void testaQueAConexaoComServidorFunciona() {

        String result = target.path("carrinhos/1").request().get(String.class);
        String result2 = target.path("projetos/1").request().get(String.class);
        System.out.println(result);
        System.out.println(result2);

        Carrinho carrinho = (Carrinho) new XStream().fromXML(result);
        Projeto projeto = (Projeto) new XStream().fromXML(result2);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());


    }

    @After
    public void stopServidor() {
        this.server.stop();
    }
}
