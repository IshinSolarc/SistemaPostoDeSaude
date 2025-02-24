package br.edu.utfpr.td.tsi.postosaude.persistencia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.Http2SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import br.edu.utfpr.td.tsi.postosaude.modelo.Paciente;

@Component
public class IndexadorPaciente {

	public void indexar(Paciente paciente) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", String.valueOf(paciente.getId()));
		document.addField("cpf", paciente.getCpf());
		document.addField("nome", paciente.getNome());
		document.addField("email", paciente.getEmail());
		document.addField("tipo", "paciente");

		try {
			solr.add(document);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void indexar(List<Paciente> pacientes) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Paciente paciente : pacientes) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", String.valueOf(paciente.getId()));
			document.addField("cpf", paciente.getCpf());
			document.addField("nome", paciente.getNome());
			document.addField("email", paciente.getEmail());
			document.addField("tipo", "paciente");
			documents.add(document);
		}

		try {
			solr.add(documents);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> procurar(String campo, String termo) {
		List<String> idsPaciente = new ArrayList<String>();

		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrQuery query = new SolrQuery();
		
		String queryStr = termo + ":" + campo + "*" + " AND tipo:paciente";
		
		query.setQuery(queryStr);
		
		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String idPaciente = doc.getFieldValue("id").toString();
				idsPaciente.add(idPaciente);

			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		solr.close();
		return idsPaciente;

	}
	
	public void deletar(String id) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id + " AND tipo:paciente");

		try {
			solr.deleteByQuery(query.getQuery());
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

}
