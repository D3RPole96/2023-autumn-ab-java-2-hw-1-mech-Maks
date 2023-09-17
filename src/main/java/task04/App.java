package task04;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // iterator vs forEach
        // 1. Первое различие заключается в том, что в forEach нельзя менять коллекцию

        // К примеру: мы хотим удалить все четные числа из листа
        List<Integer> firstExampleList = new ArrayList<>(List.of(1, 2, 3, 4));
        Iterator<Integer> itr1 = firstExampleList.iterator();
        while (itr1.hasNext())
        {
            if (itr1.next() % 2 == 0) {
                itr1.remove();
            }
        }
        System.out.println(firstExampleList); // В данном случае мы сделаем это без проблем и получим [1, 3]

        // Теперь попробуем сделаеть тоже самое, только с использованием forEach
        try {
            List<Integer> finalFirstExampleList = new ArrayList<>(List.of(1, 2, 3, 4)); // Про это в следующем примере
            finalFirstExampleList.forEach(element -> {
                if (element % 2 == 0) {
                    finalFirstExampleList.remove(element);
                }
            });
        }
        catch (Exception e) {
            System.out.println(e); // В данном случае у нас не получится сделать это, и мы получим ConcurrentModificationException
        }

        // 2. ForEach не может работать с не final (или effectively final) переменной

        // К примеру: хотим найти самое большое нечетное число в листе
        List<Integer> secondExampleList = new ArrayList<>(List.of(1, 2, 3, 4));
        Integer maxOddNumber = null;
        Iterator<Integer> itr2 = secondExampleList.iterator();
        while (itr2.hasNext())
        {
            int element = itr2.next();
            if (element % 2 != 0 && (maxOddNumber == null || element > maxOddNumber)) {
                maxOddNumber = element;
            }
        }
        System.out.println(maxOddNumber); // Здесь мы получим искомое число 3

        // Однако если мы хотим сделать нечто подобное в ForEach:
        secondExampleList = new ArrayList<>(List.of(1, 2, 3, 4));
        maxOddNumber = null;
        secondExampleList.forEach(element -> {
            /*
            Нам не позволят написать код вот так
            if (element % 2 != 0 && (maxOddNumber == null || element > maxOddNumber)) {
                maxOddNumber = element;
             */

            // Разумеется, эту штуку можно обойти, например сделав maxOddNumber массивом, и потом обращаться к его нулевому элементу,
            // но это уже жесткий костыль и излишнее усложнение кода
        });
        System.out.println("58:38 java: local variables referenced from a lambda expression must be final or effectively final");

        // 3. Если нужно пройтись до определенного момента в коллекции, прервавшись с помощью определенного слова break,
        //    то forEach не позволит сделать это и пройдется по коллекции полностью

        // К примеру: Идем по массиву чисел до первого числа, большего 5
        List<Integer> thirdExampleList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        Iterator<Integer> itr3 = thirdExampleList.iterator();
        while (itr3.hasNext())
        {
            int element = itr3.next();
            if (element > 5) {
                break;
            }
            System.out.print(element + " ");
        }
        // Все хорошо, выведется "1 2 3 4 5", а на элементе 6 цикл прервется
        System.out.println();

        // Теперь с forEach
        thirdExampleList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        thirdExampleList.forEach(element -> {
            if (element > 5) {
                // break; - мы не сможем так написать
            }
            System.out.print(element + " ");
        });
        // Мы никак не выйдем, и мы пройдемся по всем элементам; можно решить,
        // чтобы sout вызывался, если element <= 5, но пройдемся мы все равно по всем элементам
    }
}
