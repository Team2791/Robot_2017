import matplotlib.pyplot as plt


FILENAME = "C:\\Users\\team2791\\code\\TrajectoryLib\\paths\\BLUELeftGearToLeftHopper.txt"


def main():
	left_x = []
	left_y = []

	right_x = []
	right_y = []
	with open(FILENAME) as f:
		path_name = f.readline()
		print("Plotting path " + path_name)

		num_segments = int(f.readline())

		for i in range(num_segments):
			s = f.readline().split()
			left_x.append(float(s[-2]))
			left_y.append(float(s[-1]))

		# This is bad code, should be using a method
		for i in range(num_segments):
			s = f.readline().split()
			right_x.append(float(s[-2]))
			right_y.append(float(s[-1]))


	plt.plot(left_x, left_y, 'b', right_x, right_y, 'r')
	plt.show()










if __name__ == '__main__':
	main()
