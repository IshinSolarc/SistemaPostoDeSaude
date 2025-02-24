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

import br.edu.utfpr.td.tsi.postosaude.modelo.Consulta;

@Component
public class IndexadorConsulta {

	public void indexar(Consulta consulta) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", String.valueOf(consulta.getId()));
		document.addField("idconsulta", String.valueOf(consulta.getIdConsulta()));
		document.addField("data", consulta.getData());
		document.addField("horario", consulta.getHorario());
		document.addField("medico", String.valueOf(consulta.getMedico().getId()));
		document.addField("paciente", String.valueOf(consulta.getPaciente().getId()));
		System.out.println(consulta.getDiagnostico());
		document.addField("diagnostico", consulta.getDiagnostico());
		System.out.println(consulta.getReceita());
		document.addField("receita", consulta.getReceita());
		document.addField("tipo", "consulta");

		try {
			solr.add(document);
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public void indexar(List<Consulta> consultas) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Consulta consulta : consultas) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", String.valueOf(consulta.getId()));
			document.addField("idconsulta", String.valueOf(consulta.getIdConsulta()));
			document.addField("data", consulta.getData());
			document.addField("horario", consulta.getHorario());
			document.addField("medico", String.valueOf(consulta.getMedico().getId()));
			document.addField("paciente", String.valueOf(consulta.getPaciente().getId()));
			document.addField("diagnostico", consulta.getDiagnostico());
			document.addField("receita", consulta.getReceita());
			document.addField("tipo", "consulta");
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

	public List<String> procurar(String diagnostico, String receita) {
		List<String> ids = new ArrayList<String>();

		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrQuery query = new SolrQuery();
		String queryStr = "";
		
		queryStr += "tipo:consulta";
		if (!diagnostico.isEmpty()) {
			queryStr += " AND diagnostico:" + diagnostico;
		}
		if (!receita.isEmpty()) {
			queryStr += " AND receita:" + receita;
		}

		
		query.setQuery(queryStr);
		
		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String id = doc.getFieldValue("id").toString();
				ids.add(id);
			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		solr.close();
		return ids;
	}
	
	public List<String> procuraDetalhada(String Paciente_Id, String EmDiagnostico, String EmReceita) {
		List<String> ids = new ArrayList<String>();

		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrQuery query = new SolrQuery();
		String queryStr = "paciente:" + Paciente_Id + " AND tipo:consulta";
		if (!EmDiagnostico.isEmpty()) {
			queryStr += " AND diagnostico:" + EmDiagnostico;
		}
		if (!EmReceita.isEmpty()) {
			queryStr += " AND receita:" + EmReceita;
		}

		query.setQuery(queryStr);

		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String id = doc.getFieldValue("id").toString();
				ids.add(id);
			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		solr.close();
		return ids;
	}
	
	public List<String> procurarPorPaciente(String pacienteId) {
		List<String> ids = new ArrayList<String>();

		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();

		SolrQuery query = new SolrQuery();
		query.setQuery("paciente:" + pacienteId + " AND tipo:consulta");

		QueryResponse response;
		try {
			response = solr.query(query);
			SolrDocumentList docList = response.getResults();

			for (SolrDocument doc : docList) {
				String id = doc.getFieldValue("id").toString();
				ids.add(id);
			}
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		solr.close();
		return ids;
	}
	
	public void deletar(String id) {
		String urlString = "http://localhost:8983/solr/postosaude";
		Http2SolrClient solr = new Http2SolrClient.Builder(urlString).build();
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id + " AND tipo:consulta");

		try {
			solr.deleteByQuery(query.getQuery());
			solr.commit();
			solr.close();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
}
