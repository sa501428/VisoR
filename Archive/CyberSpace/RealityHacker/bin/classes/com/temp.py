import numpy as np
import numpy.matlib
import matplotlib.pyplot as plt

data = np.matlib.zeros((3, 3))
# make sure that the normalization range includes the full range we assume later
# by explicitly including `vmin` and `vmax`
plt.imshow(data, interpolation='none', aspect=3./20, vmin=0, vmax=5)

plt.yticks(range(3), ['a', 'b', 'c'])
plt.xticks(range(3), ['a', 'b', 'c'])

plt.jet()
cb = plt.colorbar()
cb.set_ticks([0, .5, 1])  # force there to be only 3 ticks
cb.set_ticklabels(['NONE', 'LOW', 'HIGH'])  # put text labels on them

plt.show()