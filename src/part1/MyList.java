package part1;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

public class MyList<T> implements AdvancedList<T>, AuthorHolder {

    private int noOfElements = 0;

    Object[] objects = new Object[10];
//    quickSort = new Insert();   //Создание потока
//        quickSort.start();                  //Запуск потока


    @Override
    public AdvancedList<T> shuffle() {
            Collections.shuffle(Arrays.asList(objects));
        return null;
    }

    @Override
    public /*synchronized*/ AdvancedList<T> sort(Comparator<T> comparator) {

        quickSort(0, noOfElements -1, comparator);
        return null;
    }

    private /*synchronized*/ void quickSort(int leftBorder, int rightBorder, Comparator<T> comparator) {
        int leftMarker = leftBorder;
        int rightMarker = rightBorder;
        Object pivot = objects[(leftMarker + rightMarker) / 2];
        do {
            // Двигаем левый маркер слева направо пока элемент меньше, чем pivot
            while (comparator.compare((T)pivot, (T) objects[leftMarker]) > 0) {
                leftMarker++;
            }
            // Двигаем правый маркер, пока элемент больше, чем pivot
            while (comparator.compare((T) objects[rightMarker], (T)pivot) > 0) {
                rightMarker--;
            }
            // Проверим, не нужно обменять местами элементы, на которые указывают маркеры
            if (leftMarker <= rightMarker) {
                // Левый маркер будет меньше правого только если мы должны выполнить swap
                if (leftMarker < rightMarker) {
                    Object tmp = objects[leftMarker];
                    objects[leftMarker] = objects[rightMarker];
                    objects[rightMarker] = tmp;
                }
                // Сдвигаем маркеры, чтобы получить новые границы
                leftMarker++;
                rightMarker--;
            }
        } while (leftMarker <= rightMarker);

        // Выполняем рекурсивно для частей
        if (leftMarker < rightBorder) {
            quickSort(rightBorder, leftMarker, comparator);
        }
        if (leftBorder < rightMarker) {
            quickSort(rightMarker, leftBorder, comparator);
        }
    }


    @Override
    public String author() {
        return "MAD_MAX";
    }


    @Override
    public void add(T item) {
        if(noOfElements >= objects.length) objects = Arrays.copyOf(objects, objects.length * 2);
        objects[noOfElements] = item;
        noOfElements++;

    }


    @Override
    public void insert(int index, T item) throws Exception {
        if (index >= noOfElements){
            throw new IndexOutOfBoundsException();
        }
        objects[index] = item;

    }

    @Override
    public void remove(int index) throws Exception {
        if (index >= noOfElements) throw new IndexOutOfBoundsException();
        for(int i = index; i < noOfElements; i++){
            objects[i] = objects[i+1];
        }
        noOfElements--;


    }

    @Override
    public Optional<T> get(int index) {
        if (index >= noOfElements) throw new IndexOutOfBoundsException();
        return Optional.of(((T) objects[index]));
    }

    @Override
    public int size() {
        return noOfElements;
    }

    @Override
    public void addAll(SimpleList<T> list) {
        for (int i = noOfElements; i < list.size(); i++){
            add((T)list.get(i));
        }

    }

    @Override
    public int first(T item) {
        for(int i = 0; i < noOfElements; i++){
            if(objects[i] == item)
                return i;
        }
        return -1;
    }

    @Override
    public boolean last(T item) {
        for(int i = noOfElements - 1; i > 0; i--){
            if(objects[i] == item)
                return true;
        }
        return false;
    }

    @Override
    public boolean contains(T item) {
        for(int i = 0; i < noOfElements; i++){
            if(objects[i] == item)
                return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return noOfElements == 0;
    }
}
