
package SelectMedian;

/**
 *
 * @author Rahnuma Islam Nishat January 20, 2017 CSC 226 - Spring 2017 Modify by
 *         Mamoutou Sangare on January 26 2017
 */
public class SelectMedian {

	// return the kth element in an array A
	public static int LinearSelect(int[] A, int k) {

		if (A == null || A.length == 0)
			return -1;

		int from = 0;
		int to = A.length - 1;
		k = k - 1;

		while (to > from) {
			int j = partition(A, from, to);
			if (j < k)
				from = j + 1;
			else if (j > k)
				to = j - 1;
			else
				return A[k];
		}
		return A[k];
	}

	private static int partition(int arr[], int left, int right) {
		int i = left, j = right;
		int tmp;
		int pivot = arr[(left + right) / 2];

		while (i <= j) {
			while (arr[i] < pivot)
				i++;
			while (arr[j] > pivot)
				j--;
			if (i <= j) {
				tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				i++;
				j--;
			}
		}
		return i;
	}

	private static double roundUp(double d) {
		return d > (int) d ? (int) d + 1 : d;
	}

	public static void main(String[] args) {
		int[] A = { 50, 54, 49, 49, 48, 49, 56, 52, 51, 52, 50, 59 };
		double len = A.length;
		double median = len / 2;
		median = roundUp(median);
		int medianPosition = (int) median;

		System.out.println("The length is " + A.length + "\n" + "Median position is " + medianPosition);
		System.out.println("The median weight is " + LinearSelect(A, medianPosition));
	}
}