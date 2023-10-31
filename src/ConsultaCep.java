import models.Cep;
import org.json.JSONObject;
import services.CepService;

import javax.swing.*;
import java.util.ArrayList;

public class ConsultaCep {
    static ArrayList<Cep> ceps = new ArrayList<Cep>();

    public static void main(String[] args) {

        //Para que o loop funcione, é necessario ao menos um CEP cadastrado
        ceps.add(new Cep("25"));
        String cepDigitado = JOptionPane.showInputDialog("Digite o CEP");
        if (validaCep(cepDigitado)){
            Cep cep = pesquisaCep(cepDigitado);
            //Printa os dados do objeto cep que foi retornado
            JOptionPane.showMessageDialog(
                    null,
                    "CEP: " + cep.getCep() + "\n" +
                            "Logradouro: " + cep.getLogradouro() + "\n" +
                            "Complemento: " + cep.getComplemento() + "\n" +
                            "Bairro: " + cep.getBairro() + "\n" +
                            "Localidade: " + cep.getLocalidade() + "\n" +
                            "UF: " + cep.getUf() + "\n" +
                            "IBGE: " + cep.getIbge() + "\n" +
                            "GIA: " + cep.getGia()
            );
        }else {
            JOptionPane.showMessageDialog(null, "CEP inválido!");
        }
    }

    private static Cep pesquisaCep(String cepDigitado){
        for (Cep cep : ceps) {
            if (cep.getCep().equals(cepDigitado)) {
                return cep;
            }else {
                CepService cepService = new CepService();
                String json = cepService.getHttp("https://viacep.com.br/ws/" + cepDigitado + "/json/");
                JSONObject obj = new JSONObject(json);
                System.out.println(obj);
                Cep cepObj = new Cep(
                        obj.getString("cep"),
                        obj.getString("logradouro"),
                        obj.getString("complemento"),
                        obj.getString("bairro"),
                        obj.getString("localidade"),
                        obj.getString("uf"),
                        obj.getString("ibge"),
                        obj.getString("gia")
                );
                ceps.add(cepObj);
                System.out.println("CEP adicionado com sucesso!");
                return cepObj;
            }
        }
        return null;
    }

    private static boolean validaCep(String cep){
        if (cep.length() == 8){
            return true;
        }else {
            return false;
        }
    }
}
