package logisticequation.ui;

import java.awt.Color;

interface ColorMake {

    Color getColor(int iteracao, int maxIteracoes);
}

class SimpleColorMake implements ColorMake {

    public Color getColor(int iteracao, int maxIteracoes) {
        return Color.RED;
    }
}

/**
 *
 * <table>
 *  <tr>
 *      <th>Porcentagem</th>
 *      <th>Cor</th>
 *      <th>Red</th>
 *      <th>Green</th>
 *      <th>Blue</th>
 *      <th>Código Hexadecimal</th>
 *  </tr>
 *  <tr>
 *      <td>0 %</td>
 *      <td>Azul</td>
 *      <td>0</td>
 *      <td>0</td>
 *      <td>FF</td>
 *      <td>#0000FF</td>
 *  </tr>
 *  <tr>
 *      <td>25 %</td>
 *      <td>Verde</td>
 *      <td>0</td>
 *      <td>FF</td>
 *      <td>0</td>
 *      <td>#00FF00</td>
 *  </tr>
 *  <tr>
 *      <td>50 %</td>
 *      <td>Amarelo</td>
 *      <td>FF</td>
 *      <td>F0</td>
 *      <td>0</td>
 *      <td>#FFF000</td>
 *  </tr>
 *  <tr>
 *      <td>75 %</td>
 *      <td>Laranja</td>
 *      <td>FF</td>
 *      <td>A5</td>
 *      <td>0</td>
 *      <td>#FFA500</td>
 *  </tr>
 *  <tr>
 *      <td>100 %</td>
 *      <td>Vermelho</td>
 *      <td>FF</td>
 *      <td>0</td>
 *      <td>0</td>
 *      <td>#FF0000</td>
 *  </tr>
 * </table>
 *
 */
class RastroColorMake implements ColorMake {

    private static final int MAX_COLOR = 0xFF;

    private int calcula(int porcentagem, int porcentagemInicial, int porcentagemFinal, int corInicial, int corFinal) {
        return (porcentagem - porcentagemInicial) * (corFinal - corInicial) / (porcentagemFinal - porcentagemInicial) + corInicial;
    }

    private int getRed(int porcentagem) {
        int red;

        if (porcentagem < 25) {
            red = 0;
        } else if (porcentagem < 50) {
            red = calcula(porcentagem, 25, 50, 0, MAX_COLOR);
        } else {
            red = MAX_COLOR;
        }

        return red;
    }

    private int getGreen(int porcentagem) {
        int green = 0;

        if (porcentagem < 25) {
            green = calcula(porcentagem, 0, 25, 0, MAX_COLOR);
        } else if (porcentagem < 50) {
            green = calcula(porcentagem, 25, 50, MAX_COLOR, 0xF0);
        } else if (porcentagem < 75) {
            green = calcula(porcentagem, 50, 75, 0xF0, 0xA5);
        } else {
            green = calcula(porcentagem, 75, 100, 0xA5, 0);
        }

        return green;
    }

    private int getBlue(int porcentagem) {
        int blue;

        if (porcentagem < 25) {
            blue = calcula(porcentagem, 0, 25, MAX_COLOR, 0);
        } else {
            blue = 0;
        }

        return blue;
    }

    public Color getColor(int iteracao, int maxIteracoes) {
        int porcentagem = iteracao * 100 / maxIteracoes;
        return new Color(
                this.getRed(porcentagem),
                this.getGreen(porcentagem),
                this.getBlue(porcentagem));
    }
}
