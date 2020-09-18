import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//https://en.wikipedia.org/wiki/ANSI_escape_code
//ANSI escape codes were used here for debugging and printing progress

class Benchmarking {
    public static void main(String[] args) throws Throwable {
        if (args.length == 0)
            args = getFiles();
        for (String fileName : args) {
            Benchmarker benchmarker = new Benchmarker();
            try {
                Scanner fileScanner = new Scanner(new File(fileName));
                LinkedList<Integer> list = new LinkedList<Integer>();
                LinkedList<Integer>.LinkedListIterator iter;
                String benchmarkID;
                // // Using the built in iterator and the insert method result in slow insert times
                // // with this soltion, very close to n^2
                // // As such, this method was actually too slow to leave in the final code
                // benchmarkID = "insertNaive";
                // benchmarker.start(benchmarkID);
                // while (fileScanner.hasNextInt()) {
                //     int toInsert = fileScanner.nextInt();
                //     int insertionIndex = 0;
                //     for (int otherValue : list) {
                //         if (toInsert < otherValue)
                //             break;
                //         insertionIndex++;
                //     }
                //     list.insert(toInsert, insertionIndex);
                //     if (list.length % 50000 == 0) {
                //         System.out.printf("%d items\n", list.length);
                //     }
                // }
                // benchmarker.end(benchmarkID);

                // Using the iterator, this insert solution can use the node structure directly,
                // doing it in at most 2/3 the time
                list.clear();
                fileScanner = new Scanner(new File(fileName));
                benchmarkID = "insertImproved";
                benchmarker.start(benchmarkID);
                while (fileScanner.hasNextInt()) {
                    int toInsert = fileScanner.nextInt();
                    iter = list.iterator();
                    while (iter.hasNext() && iter.currentNode.nextNode.data < toInsert) {
                        iter.next();
                    }
                    iter.insertAfter(toInsert);
                    if (list.length % 50000 == 0) {
                        System.out.printf("%d items\n", list.length);
                    }
                }
                benchmarker.end(benchmarkID);

                // This median solution simply queries the list once or twice, as necessary
                benchmarkID = "medNaive";
                benchmarker.start(benchmarkID);
                int medN;
                if (list.length % 2 == 0) {
                    medN = (list.get(list.length / 2 - 1) + list.get(list.length / 2)) / 2;
                } else {
                    medN = list.get(list.length / 2);
                }
                benchmarker.end(benchmarkID);

                // This max solution simply iterates to the end of the list and then access it
                // (I iterated manually because I optimized last-element access in the get
                // method)
                benchmarkID = "maxNaive";
                benchmarker.start(benchmarkID);
                iter = list.iterator();
                while (iter.hasNext())
                    iter.next();
                int maxN = iter.currentNode.data;
                benchmarker.end(benchmarkID);

                // This better median solution manually iterates to the mediean index, allowing
                // for quick next access, roughly halving the time
                // The iterator stops 1 index short of the typical median index to ensure the
                // ability to access the value before it as well
                benchmarkID = "medImproved";
                benchmarker.start(benchmarkID);
                int medI;
                int medIndex = list.length / 2 - 1;
                LinkedList<Integer>.LinkedListNode curr = list.head.nextNode;
                for (int i = 0; i < medIndex && curr.nextNode != null; curr = curr.nextNode, i++) {
                }
                if (list.length % 2 == 0) {
                    medI = (curr.data + curr.nextNode.data) / 2;
                } else {
                    medI = curr.data;
                }
                benchmarker.end(benchmarkID);

                // Once again, the implementation allows max search to operate in O(1)
                benchmarkID = "maxImproved";
                benchmarker.start(benchmarkID);
                int maxI = list.last.data;
                benchmarker.end(benchmarkID);

                // Thanks to the implementation of my LinkedList class, this min search is O(1)
                // for both
                benchmarkID = "min";
                benchmarker.start(benchmarkID);
                int min = list.head.nextNode.data;
                benchmarker.end(benchmarkID);

                System.out.printf(
                        "\n-----------%s Bench Summary----------\n" + "Min: %d\n" + "Med: %d\n" + "Max: %d\n"
                                + "\n----------------------Time(Naive Attempt)---------------------\n"
                                + "Insert: %13dns||Min: %13dns||Med: %13dns||Max: %13dns\n"
                                + "\n----------------------Time(Improved Attempt)---------------------\n"
                                + "Insert: %13dns||Min: %13dns||Med: %13dns||Max: %13dns\n",
                        fileName, min, medI, maxI, benchmarker.getBenchmark("insertNaive"),
                        benchmarker.getBenchmark("min"), benchmarker.getBenchmark("medNaive"),
                        benchmarker.getBenchmark("maxNaive"), benchmarker.getBenchmark("insertImproved"),
                        benchmarker.getBenchmark("min"), benchmarker.getBenchmark("medImproved"),
                        benchmarker.getBenchmark("maxImproved"));

            } catch (FileNotFoundException e) {
                System.out.printf("File %s not found\n", e);
            }
        }
    }

    public static String[] getFiles() {
        Scanner in = new Scanner(System.in);
        LinkedList<String> paths = new LinkedList<>();
        String currentPath;
        while (true) {
            System.out.printf("\033[s");
            System.out.printf("\033[uPlease enter a file name%s('/' is used as a path delimiter): ",
                    paths.length == 0 ? "" : ", or type RUN to begin benchmarking");
            // https://stackoverflow.com/questions/2733255/java-doesnt-work-with-regex-s-says-invalid-escape-sequence
            // Referenced to clear up minor regex confusion
            while (!(currentPath = in.nextLine()).matches("(([^\n\t\r/]+/)*[^\n\t\r/]+)|RUN")) {
                System.out.printf(
                        "\033[uThat was an invalid path. Please enter a valid file name%s('/' is used as a path delimiter): ",
                        paths.length == 0 ? "" : ", or type RUN to begin benchmarking");
            }
            if (currentPath.equals("RUN")) {
                break;
            }
            paths.insert(currentPath);

        }
        String[] pathsArr = new String[paths.length];
        LinkedList<String>.LinkedListIterator iter = paths.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            pathsArr[i] = iter.next();
        }
        in.close();
        return pathsArr;
    }

}