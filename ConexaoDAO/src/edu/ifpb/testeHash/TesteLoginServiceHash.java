package edu.ifpb.testeHash;

import java.security.MessageDigest;

public class TesteLoginServiceHash {
	 public static void main(String[] args) {
	        String senha = ""; // Senha que você quer gerar o hash
	        String senhaHash = hashSenha(senha); // Gera o hash
	        System.out.println("Hash da senha: " + senhaHash); // Exibe o hash
	    }

	    public static String hashSenha(String senha) {
	        try {
	            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
	            byte[] messageDigest = algoritmo.digest(senha.getBytes("UTF-8"));
	            StringBuilder hexString = new StringBuilder();

	            for (byte b : messageDigest) {
	                String hex = Integer.toHexString(0xff & b);
	                if (hex.length() == 1) {
	                    hexString.append('0');
	                }
	                hexString.append(hex);
	            }
	            return hexString.toString();
	        } catch (Exception e) {
	            throw new RuntimeException("Erro ao gerar hash da senha.", e);
	        }
	    }
	}


