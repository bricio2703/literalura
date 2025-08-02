package com.alura.literarula;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class LiterarulaApplication {
	private static final String API_URL = "https://gutendex.com/books";

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nCatálogo de Livros");
			System.out.println("1. Listar livros populares");
			System.out.println("2. Buscar livro por título");
			System.out.println("3. Buscar livro por autor");
			System.out.println("4. Ver detalhes de um livro (ID)");
			System.out.println("5. Buscar livros por idioma");
			System.out.println("6. Sair");
			System.out.print("Escolha uma opção: ");
			int opcao = scanner.nextInt();
			scanner.nextLine(); // Limpa buffer

			switch (opcao) {
				case 1:
					buscarLivros("");
					break;
				case 2:
					System.out.print("Digite o título: ");
					String titulo = scanner.nextLine();
					buscarLivros("search=" + titulo);
					break;
				case 3:
					System.out.print("Digite o autor: ");
					String autor = scanner.nextLine();
					buscarLivros("search=" + autor);
					break;
				case 4:
					System.out.print("Digite o ID do livro: ");
					String id = scanner.nextLine();
					buscarLivroPorId(id);
					break;
				case 5:
					System.out.print("Digite o código do idioma (ex: pt, en, fr): ");
					String idioma = scanner.nextLine();
					buscarLivros("languages=" + idioma);
					break;
				case 6:
					System.out.println("Saindo...");
					return;
				default:
					System.out.println("Opção inválida.");
			}
		}
	}



	private static void buscarLivros(String query) throws Exception {
		String urlStr = API_URL + (query.isEmpty() ? "" : "?" + query);
		String resposta = requisicaoHttp(urlStr);
		System.out.println("Livros encontrados:\n" + resposta);
	}

	private static void buscarLivroPorId(String id) throws Exception {
		String urlStr = API_URL + "/" + id;
		String resposta = requisicaoHttp(urlStr);
		System.out.println("Detalhes do livro:\n" + resposta);
	}

	private static String requisicaoHttp(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuilder conteudo = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			conteudo.append(inputLine).append("\n");
		}
		in.close();
		return conteudo.toString();
	}
}