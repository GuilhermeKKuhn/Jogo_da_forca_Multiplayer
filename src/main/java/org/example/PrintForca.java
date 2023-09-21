package org.example;

public class PrintForca {
    private String forca = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "| \n" +
            "| \n" +
            "|           ";
    private String forca1 = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "|   |\n" +
            "| \n" +
            "|           ";
    private String forca2 = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "|  /|\n" +
            "| \n" +
            "|           ";
    private String forca3 = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "|  /|\\\n" +
            "| \n" +
            "|           ";
    private String forca4 = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "|  /|\\\n" +
            "|  /\n" +
            "|           ";

    private String forca5 = "+---+\n" +
            "|   |\n" +
            "|   O\n" +
            "|  /|\\\n" +
            "|  / \\\n" +
            "|           ";

    public String getForca() {
        return forca;
    }

    public String getForca1() {
        return forca1;
    }

    public String getForca2() {
        return forca2;
    }

    public String getForca3() {
        return forca3;
    }

    public String getForca4() {
        return forca4;
    }

    public String getForca5() {
        return forca5;
    }

    public String validaForca(int erros) {
        switch (erros) {
            case 0:
                return getForca();
            case 1:
                return getForca1();
            case 2:
                return getForca2();
            case 3:
                return getForca3();
            case 4:
                return getForca4();
            case 5:
                return getForca5();
        }
        return "Erro";
    }
}
