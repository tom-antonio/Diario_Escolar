package controller;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JToggleButton;

public class DiarioController {
    
    public double calcularMedia(List<Double> notas) {
        if (notas.isEmpty()) return 0;
        
        double soma = 0;
        for (Double nota : notas) {
            soma += nota;
        }
        
        return soma / notas.size();
    }
    
    public void atualizarExibicao(List<Double> notas, DefaultListModel<String> modeloLista, JToggleButton togStatus) {
        // Limpa lista
        modeloLista.clear();
        
        // Adiciona notas na JList
        for (int i = 0; i < notas.size(); i++) {
            modeloLista.addElement(String.format("%.2f", notas.get(i)));
        }
        
        // Atualiza status
        if (notas.isEmpty()) {
            togStatus.setSelected(true);
            atualizarTextoStatus(togStatus);
        } else {
            double media = calcularMedia(notas);
            togStatus.setSelected(media > 6);
            atualizarTextoStatus(togStatus);
        }
    }
    
    public void atualizarTextoStatus(JToggleButton togStatus) {
        if (togStatus.isSelected()) {
            togStatus.setText("Aprovado");
        } else {
            togStatus.setText("Reprovado");
        }
    }
}