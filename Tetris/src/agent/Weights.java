package agent;

public 
class Weights {
	// ���ǿ��ѧϰ���������
	static final Mat W1 = new Mat(new double[][] {
		   {-0.01651163,  0.34468436,  0.0283937 ,  0.39938846, -0.07314599,
	        -0.07512268,  0.03740942, -0.54275376,  0.0426284 ,  0.39936194,
	         0.02504063, -0.2627911 ,  0.07536062, -0.17519884, -0.10817757,
	        -0.4917446 ,  0.36513335, -0.18851538,  0.5664596 ,  0.04773921,
	        -0.2773039 ,  0.24177934,  0.48466706,  0.5967761 ,  0.35963035,
	        -0.19756298, -0.06636518,  0.24423587, -0.00950086, -0.29310367,
	        -0.20840442, -0.01640902},
	       {-0.00681597,  0.30753654,  0.07549476, -0.3415628 , -0.2980703 ,
	        -0.39042494, -0.19439991,  0.28259248, -0.32625037, -0.01910243,
	         0.14293706, -0.27273265, -0.19592302,  0.12003246, -0.05251703,
	         0.15102859, -0.25682497, -0.24161837, -0.03888951,  0.07618105,
	        -0.04216934, -0.53607154,  0.16393337, -0.21784818,  0.22746235,
	         0.27555427,  0.03423145,  0.09322572, -0.04743786,  0.23319693,
	         0.00160155,  0.3192782 },
	       { 0.19747724, -0.21842232,  0.25555947,  0.15689372,  0.16545334,
	         0.11815739, -0.25561118, -0.02718376,  0.1782429 , -0.2475167 ,
	        -0.21113025, -0.03103754,  0.0169603 ,  0.05575809,  0.05219048,
	         0.29241225, -0.41322547, -0.09027725,  0.2481831 , -0.11811313,
	         0.11175636,  0.1437482 , -0.05412209,  0.2965223 ,  0.08325636,
	         0.3814447 ,  0.28654072,  0.0373126 , -0.22492315,  0.17458333,
	        -0.18739994,  0.24679047},
	       { 0.1088298 , -0.4000116 ,  0.17744434,  0.16053732, -0.42789355,
	        -0.26903757, -0.20006561,  0.37683538, -0.482834  ,  0.05626695,
	        -0.15858752,  0.08312331, -0.03036175, -0.02035495, -0.23039187,
	         0.17968091, -0.01130352, -0.2144803 , -0.08537444, -0.20054771,
	         0.26379207,  0.12553559,  0.15663564, -0.23715942,  0.03228987,
	        -0.01910708, -0.34025452, -0.27915463,  0.19089763, -0.36278087,
	        -0.15522143,  0.00780994},
	});
	static final Mat b1 = new Mat(new double[][] {
		   { 1.0179908 ,  0.        , -0.35758832,  1.1427405 ,  0.34941334,
		     0.31185535,  0.        , -1.2389779 ,  0.39890826, -0.06074943,
		     0.        ,  1.3556161 , -0.02715468,  1.6319414 ,  0.        ,
		    -1.2597098 , -0.0269828 ,  0.        ,  0.6059633 ,  0.        ,
		    -1.1925888 ,  0.9858664 , -0.25532088,  0.3279781 ,  1.0137436 ,
		    -1.4170384 ,  0.25193614,  0.        ,  1.4543942 ,  0.24047135,
		     0.        ,  1.1187135 }
	});
	static final Mat W2 = new Mat(new double[][] {
		   {-8.62428397e-02, -2.09346801e-01, -1.38053387e-01,
	        -5.33341505e-02,  2.72050470e-01, -2.62551516e-01,
	         1.10707842e-01, -3.71048786e-02, -1.70081571e-01,
	        -1.95080951e-01, -3.02629799e-01,  2.82063276e-01,
	         1.58599749e-01,  3.39980930e-01, -2.15191439e-01,
	        -3.26934271e-02, -4.79627699e-02,  2.84508109e-01,
	         1.86528906e-01,  3.14678699e-01, -1.24731563e-01,
	         5.40043749e-02, -9.01859179e-02, -1.01118907e-01,
	        -2.48102784e-01,  2.34729320e-01,  1.13333941e-01,
	         1.90441772e-01,  1.95538506e-01, -3.15866679e-01,
	         2.28798956e-01,  3.00180286e-01},
	       { 2.83472329e-01, -2.82068104e-01,  2.45377392e-01,
	         9.25675333e-02, -8.20606500e-02, -1.54100522e-01,
	         1.11597180e-02, -1.24192297e-01,  8.62801075e-03,
	        -2.65943319e-01, -7.10129738e-02, -1.48625627e-01,
	        -2.09072798e-01,  2.72805363e-01, -1.45778954e-02,
	        -1.31335095e-01, -9.26903933e-02, -1.16080701e-01,
	        -1.36869922e-01, -2.17123926e-02,  1.67011887e-01,
	        -1.48014694e-01,  2.09903866e-01,  1.21017724e-01,
	         9.44628417e-02,  2.02571154e-02,  2.43155628e-01,
	         7.68460929e-02, -2.40006834e-01,  2.56626278e-01,
	        -2.75568664e-02, -2.31499076e-01},
	       { 1.41517311e-01, -1.29798904e-01, -1.96763858e-01,
	        -7.95575529e-02, -1.60116494e-01,  2.33757526e-01,
	        -7.46461600e-02,  2.11117536e-01, -1.89619571e-01,
	        -2.67320484e-01, -2.46793956e-01, -6.72725588e-02,
	         2.59690732e-01, -1.05326787e-01, -2.33148009e-01,
	         6.10215366e-02, -1.70982510e-01, -8.05798024e-02,
	         2.54786849e-01,  2.28850823e-02,  2.40595132e-01,
	        -3.14555198e-01,  2.05488011e-01, -9.22452509e-02,
	        -1.34120941e-01, -2.67896950e-01, -2.80642122e-01,
	         2.89959945e-02,  2.89559871e-01,  1.47527680e-01,
	        -3.36450696e-01, -2.45232522e-01},
	       {-2.85074353e-01, -1.10782437e-01,  2.43407995e-01,
	        -4.97197285e-02,  2.96009898e-01,  1.71999082e-01,
	         1.28120020e-01,  2.06907704e-01,  1.20406121e-01,
	         4.87171710e-02,  7.94785023e-02,  2.94495255e-01,
	        -1.86241761e-01,  7.51872212e-02,  2.20562309e-01,
	         1.64770603e-01,  6.70631677e-02, -1.69464555e-02,
	        -2.38606274e-01, -3.05076718e-01,  2.97593653e-01,
	        -1.99355617e-01, -2.01929644e-01,  4.10994500e-01,
	         1.41624317e-01,  5.06465733e-02,  4.01179612e-01,
	        -2.05051422e-01,  2.50994176e-01, -2.57675480e-02,
	         3.60683233e-01, -3.88161331e-01},
	       {-2.40671977e-01,  8.55104476e-02,  4.41530943e-02,
	        -3.23228359e-01,  1.50936678e-01, -2.95716614e-01,
	         1.90433443e-01,  4.04389858e-01, -7.32447505e-02,
	         1.21564418e-01,  1.89668745e-01,  5.23140371e-01,
	         8.60310867e-02,  6.47032201e-01,  1.07230581e-01,
	         1.49643794e-01,  6.22423887e-01,  6.16475463e-01,
	        -2.35956624e-01,  2.97514230e-01,  2.94014871e-01,
	         1.31699592e-01,  2.21114799e-01,  3.75594974e-01,
	        -1.86311483e-01,  1.66411966e-01,  4.44283724e-01,
	         4.20097798e-01,  6.39312327e-01,  1.64881721e-01,
	         5.47692120e-01,  1.77474752e-01},
	       {-8.14277008e-02,  1.28535315e-01, -1.78931355e-02,
	         1.42145947e-01,  1.63424745e-01,  5.09447269e-02,
	         3.03803593e-01,  2.50775665e-01, -1.53362483e-01,
	         1.32053047e-01, -2.81199545e-01,  3.05975556e-01,
	         2.14505177e-02, -1.64347723e-01,  3.06870520e-01,
	         4.64139692e-02, -4.08475474e-02,  1.61085397e-01,
	         2.89420933e-01,  4.03779596e-01,  2.88600355e-01,
	         1.12119615e-01, -5.77289239e-02,  4.86918479e-01,
	         6.34779334e-02,  1.79798871e-01, -6.47754893e-02,
	         8.85846093e-02,  1.72584236e-01,  5.12571484e-02,
	         1.27311841e-01, -5.92039749e-02},
	       { 2.57163018e-01, -1.77415714e-01, -6.40213490e-02,
	        -2.30019212e-01,  2.85489351e-01, -8.14687610e-02,
	        -4.59335744e-02, -1.03685215e-01, -1.35767758e-01,
	         6.25884533e-03, -1.89610153e-01,  6.50009513e-03,
	         1.51570410e-01, -1.26988366e-01,  1.92923188e-01,
	         2.13774830e-01, -2.16667920e-01, -2.43332982e-03,
	        -1.50990620e-01, -1.90665871e-01,  2.16925830e-01,
	         1.46654040e-01,  1.29293650e-01, -4.35882211e-02,
	        -1.36438861e-01, -2.91902959e-01, -7.01967627e-02,
	        -1.65696934e-01, -2.23368645e-01,  8.96006823e-04,
	         9.94348228e-02,  1.66758955e-01},
	       { 2.44379342e-01,  4.70445193e-02, -3.03138435e-01,
	        -7.44215632e-03, -2.02730358e-01, -1.08290948e-01,
	        -3.19461495e-01, -7.60323107e-02, -1.52794912e-01,
	        -2.18030483e-01,  4.94448580e-02,  5.98887280e-02,
	         1.29855260e-01, -2.56264061e-01,  5.83451837e-02,
	        -2.45066639e-02, -1.42063990e-01, -1.69491936e-02,
	        -1.28152162e-01,  2.82504231e-01, -7.31586590e-02,
	        -2.32683823e-01,  2.20532641e-01, -3.43833625e-01,
	         5.49564976e-03, -2.59600520e-01,  2.84004547e-02,
	        -1.55200601e-01, -1.91704899e-01, -1.69917613e-01,
	         7.05946833e-02,  1.12841144e-01},
	       {-1.99482545e-01,  2.90748209e-01, -1.60049483e-01,
	         1.17275767e-01,  1.17836617e-01,  2.01272979e-01,
	         1.47028476e-01,  3.85085344e-01,  1.26010239e-01,
	        -1.38116047e-01,  2.28239790e-01,  3.55040163e-01,
	         5.27522922e-01,  3.66965741e-01,  1.90601468e-01,
	         6.64856434e-01,  2.68842757e-01,  6.59557879e-01,
	         7.91368559e-02,  1.44685864e-01,  3.91758829e-01,
	        -2.45078623e-01, -2.32290179e-01,  1.63328797e-01,
	        -2.85412550e-01, -5.56917787e-02,  2.82750994e-01,
	         5.18040240e-01,  1.93227530e-01, -5.80135584e-02,
	         2.93337107e-01, -3.75714749e-01},
	       { 2.69843608e-01, -1.53019950e-01, -4.37774360e-02,
	         1.37741536e-01, -2.01515943e-01, -2.72021830e-01,
	        -1.93587035e-01, -4.52915505e-02, -2.50447214e-01,
	        -2.22484022e-01, -1.38265923e-01,  8.06704760e-02,
	        -2.73988664e-01, -2.12181002e-01, -9.19544771e-02,
	         1.43797649e-02,  2.16939196e-01, -3.07861179e-01,
	         3.11574694e-02, -1.02389930e-02,  1.15882508e-01,
	        -2.20552653e-01,  9.81177837e-02, -5.20769842e-02,
	         8.74687433e-02,  1.37823761e-01,  9.44233537e-02,
	         3.05228800e-01, -8.82473402e-03, -1.71628594e-01,
	        -2.62282461e-01,  1.42110556e-01},
	       {-2.09287405e-02, -1.26088485e-01, -1.96775794e-01,
	         2.91808993e-01,  2.86299020e-01, -3.22106481e-02,
	        -1.70883253e-01, -2.36048013e-01, -1.81807563e-01,
	        -2.21201107e-01,  9.63907838e-02, -2.11668193e-01,
	         5.79574108e-02, -1.27126187e-01,  1.30564272e-02,
	         1.45947546e-01,  3.93050015e-02, -1.99334398e-01,
	         2.59009153e-01, -2.31458634e-01, -2.45038897e-01,
	        -1.19949877e-01, -1.36328846e-01, -1.72265738e-01,
	        -8.19364041e-02,  1.83653533e-01, -2.96874225e-01,
	         9.06038284e-02, -2.14148462e-01,  1.98380262e-01,
	        -1.44832671e-01, -2.05877364e-01},
	       {-2.11324677e-01, -4.67868336e-02,  2.76642069e-02,
	         1.53458700e-01,  2.48056695e-01,  1.94766030e-01,
	         3.63578737e-01,  4.43622530e-01, -2.17100024e-01,
	        -1.64099336e-02, -4.05514166e-02,  3.16892982e-01,
	         1.10516749e-01,  5.66565134e-02,  2.06310377e-01,
	         4.73196566e-01,  2.73642931e-02,  4.18382198e-01,
	        -3.43513340e-01, -3.59014213e-01,  2.98012972e-01,
	         1.85320318e-01, -3.95552695e-01,  5.49919128e-01,
	        -2.73189873e-01,  2.49230355e-01,  6.03844285e-01,
	        -6.88082501e-02,  1.92744434e-01,  3.90802063e-02,
	         2.48914082e-02,  2.69353259e-02},
	       {-2.33807340e-01,  2.13114649e-01,  2.11933762e-01,
	         1.74436152e-01,  2.64714420e-01,  1.70259073e-01,
	         1.91636030e-02,  2.53294110e-01,  5.71556687e-02,
	        -2.21172124e-01, -3.06166232e-01, -2.62489706e-01,
	        -7.89570715e-03, -1.26952948e-02,  1.54519156e-01,
	        -2.85391212e-01,  3.73366177e-02, -1.48782089e-01,
	        -2.46608555e-01,  3.44876885e-01,  2.58185655e-01,
	        -1.91024601e-01,  1.43885747e-01,  2.43501529e-01,
	         2.40509525e-01,  2.35750765e-01, -1.28925472e-01,
	         1.33230209e-01, -2.48171136e-01,  1.26820683e-01,
	        -2.13435128e-01, -2.60164917e-01},
	       {-8.43765080e-01,  9.68742013e-01, -1.15072489e-01,
	        -8.67722034e-02,  9.60694730e-01, -1.11225918e-01,
	         6.66021407e-01,  9.04338062e-01, -1.20651186e-01,
	        -1.96219534e-01,  2.22460315e-01,  8.58789146e-01,
	         8.55693817e-01,  8.19916368e-01,  5.83947062e-01,
	         7.02773511e-01,  1.03125668e+00,  9.95900750e-01,
	        -4.55299556e-01, -6.74508810e-01,  8.46552312e-01,
	        -2.83973634e-01, -5.08592308e-01,  1.31995797e+00,
	        -9.29544419e-02, -4.28101122e-02,  1.22633743e+00,
	         4.12324548e-01,  9.90192771e-01, -1.47684170e-02,
	         7.30242252e-01, -3.60708296e-01},
	       {-2.08678305e-01, -2.13208795e-02,  1.76746964e-01,
	        -2.31409967e-02,  8.12383592e-02, -1.74973041e-01,
	        -6.82066828e-02,  2.44234174e-01,  2.45512426e-02,
	         2.29623586e-01,  5.60869277e-02,  2.54365534e-01,
	         1.96059912e-01, -2.09081650e-01,  1.96858734e-01,
	        -1.74163908e-01,  7.11765587e-02, -1.14303425e-01,
	        -1.51037633e-01,  6.08559847e-02,  3.33440006e-02,
	        -1.60991475e-01, -5.77641129e-02, -2.69285738e-01,
	         4.18990552e-02, -1.89389765e-01, -2.11934656e-01,
	         9.42641497e-02, -1.95337832e-01, -1.37544379e-01,
	         1.68646961e-01,  1.40351892e-01},
	       { 2.76651949e-01,  1.55890495e-01, -1.97997510e-01,
	         1.25266880e-01,  1.59644842e-01,  1.39876217e-01,
	         1.47897884e-01, -3.06615740e-01, -3.67274880e-02,
	         5.53288162e-02,  1.35803983e-01, -2.11279616e-01,
	        -2.03956410e-01, -2.52849311e-01,  5.28019108e-02,
	        -1.20205265e-02,  7.67934397e-02, -3.15036207e-01,
	         3.06406021e-01, -1.36425821e-02, -2.86327571e-01,
	         2.83305317e-01,  7.78067634e-02, -2.16321379e-01,
	        -3.22962433e-01, -1.39298871e-01, -1.99605078e-01,
	         1.92958713e-01, -2.22973078e-01, -3.78659368e-02,
	        -2.25938782e-01,  3.35991532e-01},
	       { 1.53294384e-01,  2.36641914e-01,  1.69433117e-01,
	         2.83577055e-01,  6.12396710e-02, -6.14448041e-02,
	        -1.60353914e-01,  1.65067434e-01, -4.58747447e-02,
	        -1.68482423e-01,  2.32711464e-01, -8.56807306e-02,
	        -2.26018447e-02,  2.57953256e-01,  1.40402973e-01,
	        -6.04059063e-02, -4.72783782e-02,  2.74740338e-01,
	        -1.10451192e-01,  1.11543566e-01,  1.51426241e-01,
	        -8.55832100e-02, -2.64200330e-01, -2.60020018e-01,
	        -2.59782225e-01, -2.20431834e-01, -2.21532822e-01,
	         2.25375742e-01, -9.53844860e-02, -2.02802137e-01,
	        -1.01970449e-01,  2.30954155e-01},
	       {-2.73332804e-01,  1.30679190e-01, -3.77571583e-02,
	         1.49016321e-01,  3.00831228e-01,  2.91717142e-01,
	        -1.28326014e-01, -8.76655579e-02, -1.61183611e-01,
	        -6.94329590e-02, -2.81558216e-01, -1.56204402e-01,
	        -2.99803644e-01, -2.15731546e-01,  2.55816132e-01,
	         1.30492747e-01, -1.17805481e-01,  2.65540749e-01,
	        -2.39280120e-01, -2.90039182e-01, -2.16386646e-01,
	         1.34805918e-01,  3.02184075e-01,  2.08088487e-01,
	        -7.48932362e-02,  1.55724704e-01,  8.45375657e-02,
	        -1.91281423e-01, -1.11056581e-01, -1.29450008e-01,
	        -1.07214451e-02, -4.19871807e-02},
	       { 1.12197064e-01, -2.13353932e-01, -1.04845844e-01,
	         2.28740573e-01, -1.52811229e-01, -2.92782933e-01,
	        -9.99266133e-02, -2.11201891e-01, -1.73504651e-02,
	        -1.59237131e-01, -1.70463711e-01,  9.07101855e-02,
	        -1.03636228e-01,  1.79574881e-02, -2.65240431e-01,
	        -3.86331469e-01, -3.73129815e-01, -3.90646420e-02,
	         2.21435711e-01,  2.30575785e-01, -9.45342481e-02,
	        -5.68003394e-02,  2.31732398e-01, -9.70494673e-02,
	         1.53792083e-01, -2.86654532e-02, -6.60113513e-01,
	         1.00387782e-02,  1.05741724e-01,  1.26450449e-01,
	        -2.28155762e-01,  2.44387060e-01},
	       { 1.42190129e-01,  1.55328184e-01,  2.69142658e-01,
	         2.12005764e-01, -2.99515605e-01,  6.39003813e-02,
	         2.97373265e-01,  4.23462689e-02, -2.21164465e-01,
	         1.39341056e-02, -2.18328327e-01, -2.00285017e-01,
	         1.68893635e-01, -1.35418385e-01, -5.88922650e-02,
	         4.97741401e-02, -1.10171378e-01, -3.74355316e-02,
	        -8.31448585e-02, -2.01326728e-01, -1.68969691e-01,
	        -1.13810092e-01,  1.94436401e-01, -2.59819180e-01,
	        -4.48499620e-02,  1.28994912e-01,  1.07381672e-01,
	         1.08161181e-01,  6.37239516e-02,  1.47038102e-01,
	         1.03881955e-01,  1.78387851e-01},
	       { 2.81053726e-02,  7.98101872e-02, -2.88917512e-01,
	        -2.09699407e-01, -1.71745628e-01, -2.36873940e-01,
	        -6.03975467e-02, -1.55403867e-01,  2.92174250e-01,
	         1.60243511e-01,  4.28197067e-03, -3.34299684e-01,
	        -2.41942927e-01,  1.53134570e-01, -1.51291892e-01,
	         5.10109924e-02,  1.47115678e-01, -2.07271855e-02,
	         5.40128313e-02, -1.91963866e-01, -2.36249149e-01,
	         2.17195868e-01,  5.23689017e-03, -2.84028172e-01,
	         1.99436471e-01, -9.23600644e-02, -9.56002027e-02,
	        -3.55822653e-01, -1.03360876e-01,  1.40093863e-02,
	         1.87840298e-01,  3.05973351e-01},
	       { 1.96375191e-01,  7.15031847e-02,  7.75267021e-04,
	         1.75899923e-01,  1.00949936e-01, -6.02345318e-02,
	         1.22239674e-02,  1.07328687e-02, -2.25429446e-01,
	         4.43096161e-02, -1.83255911e-01, -4.15709838e-02,
	         6.72573447e-02,  2.15980753e-01,  3.06822807e-01,
	        -1.41339123e-01, -3.52415256e-02, -9.48743969e-02,
	        -2.12841749e-01, -2.26756245e-01, -1.59752816e-01,
	        -2.89438725e-01,  4.79760952e-02,  2.21841469e-01,
	         1.55068651e-01, -2.55524695e-01,  3.98528218e-01,
	         2.42976174e-01,  1.53649360e-01, -3.04980785e-01,
	        -1.46332830e-01,  8.87048766e-02},
	       { 2.82485969e-02,  1.66832432e-01, -2.96576172e-01,
	        -1.47060916e-01, -7.86909461e-02, -2.75644153e-01,
	         1.68050453e-01,  1.78374738e-01, -2.04017669e-01,
	         2.90028900e-01, -1.78032201e-02, -3.68069597e-02,
	         1.37679532e-01, -1.49223372e-01, -2.43981391e-01,
	        -2.82919407e-01, -4.52426150e-02, -1.84615806e-01,
	         9.24727842e-02,  2.13686824e-01,  1.79068238e-01,
	         1.82091400e-01,  1.18055284e-01,  4.76203352e-01,
	         7.43743628e-02, -2.92035878e-01, -3.03085476e-01,
	        -2.97658555e-02,  1.86980352e-01,  5.27191386e-02,
	         1.39445290e-02,  2.30790481e-01},
	       {-4.78545949e-02,  2.00315788e-01, -5.21531403e-02,
	        -3.35157663e-01,  1.20671436e-01, -2.81320006e-01,
	         1.78129971e-01,  1.75448120e-01,  7.22866952e-02,
	        -1.15515456e-01, -1.89828575e-01,  1.00515954e-01,
	         1.14417046e-01, -3.43660206e-01, -2.28917167e-01,
	        -5.86275876e-01,  7.88660273e-02, -1.43882573e-01,
	         1.96503863e-01,  3.58996391e-01,  8.43378752e-02,
	        -9.70941484e-02,  4.71069485e-01,  1.08759657e-01,
	        -1.02982730e-01, -1.37272954e-01, -2.55201638e-01,
	        -2.80764103e-01,  7.49486983e-02,  1.59862146e-01,
	        -1.41582996e-01,  2.80109048e-01},
	       {-2.23673016e-01,  1.49631441e-01, -1.00874446e-01,
	        -2.63793170e-01,  1.68677926e-01,  1.00256324e-01,
	         1.62073389e-01, -1.46247312e-01, -5.61195016e-02,
	        -2.53574491e-01, -3.34230691e-01,  2.89999872e-01,
	         4.23990935e-02,  1.70503214e-01, -1.79611631e-02,
	         2.20951661e-01,  1.84837908e-01,  8.67495239e-02,
	        -1.90954342e-01,  2.96790928e-01,  1.20586790e-01,
	         7.75751621e-02,  3.22473124e-02,  1.31100655e-01,
	        -2.32496068e-01, -2.81491637e-01, -8.27265456e-02,
	        -1.30544797e-01,  2.46779636e-01, -2.00834066e-01,
	        -8.19131825e-03,  1.26480937e-01},
	       {-1.35222316e-01, -1.90327406e-01, -1.12456083e-01,
	         9.29946229e-02, -4.58371639e-01, -2.19006643e-01,
	        -1.87868491e-01, -2.32933745e-01, -2.30573490e-01,
	        -2.28690088e-01, -3.21257144e-01,  1.51049197e-01,
	         5.19217364e-02, -1.69796422e-01,  1.09831683e-01,
	        -2.82474339e-01, -1.72011673e-01, -4.32939678e-02,
	         2.34346651e-02,  3.66587460e-01,  2.18235627e-02,
	         1.68239877e-01,  3.15037489e-01, -2.99992114e-01,
	        -1.81498751e-01, -3.04155648e-01, -5.06266803e-02,
	        -3.54074426e-02, -4.04172719e-01, -1.11194938e-01,
	        -3.09715688e-01,  2.77432948e-01},
	       {-2.19587386e-01, -3.03360373e-01, -2.79824942e-01,
	        -2.00969785e-01,  8.73925257e-03, -2.73143411e-01,
	        -1.53340045e-02, -1.75586119e-01,  7.15012848e-02,
	        -1.39223903e-01,  1.28665566e-01,  1.47804394e-02,
	         4.87065017e-02,  1.44233271e-01,  1.85699418e-01,
	        -6.65279850e-02,  4.05803993e-02, -1.59648597e-01,
	         1.43952683e-01,  8.47878680e-02,  3.93331237e-03,
	         4.61180508e-02,  1.02164365e-01,  3.18925798e-01,
	        -1.11329466e-01, -2.62444556e-01, -1.31944701e-01,
	         1.99874207e-01,  1.30613610e-01, -6.32074336e-03,
	         6.59279004e-02,  3.31119001e-01},
	       { 2.98707396e-01, -2.80733079e-01, -1.45954102e-01,
	         1.86276793e-01,  2.03987151e-01, -2.39523709e-01,
	        -2.64379799e-01,  3.56170535e-03,  2.24166602e-01,
	         3.40232253e-03,  2.68976986e-02,  2.30063230e-01,
	         7.47981966e-02, -5.98169565e-02, -2.83902407e-01,
	         1.59498602e-01, -1.44695655e-01,  3.47745419e-03,
	        -9.45136547e-02, -2.19348788e-01,  1.81372106e-01,
	        -1.23226210e-01, -2.75626391e-01, -1.00442320e-01,
	         1.94576889e-01,  1.07388109e-01,  1.54674292e-01,
	        -2.75282711e-01,  2.13316351e-01,  2.58402616e-01,
	        -1.45904616e-01, -2.56145120e-01},
	       { 1.93266615e-01,  2.17985466e-01, -1.86346203e-01,
	        -1.70775220e-01,  2.19351888e-01,  4.59763817e-02,
	         2.22128451e-01,  2.83472955e-01, -2.00269461e-01,
	         8.07633996e-03,  2.10867450e-01, -2.63052788e-02,
	         1.27927689e-02,  3.48579824e-01,  2.46105045e-01,
	         8.32736641e-02,  3.50052446e-01,  3.52286428e-01,
	         1.27177969e-01, -1.01390436e-01,  1.79166600e-01,
	        -2.50532418e-01, -1.30482942e-01,  5.99510789e-01,
	         2.53491104e-01, -2.91859895e-01,  3.97774547e-01,
	        -2.71017432e-01, -1.17762148e-01,  2.23723218e-01,
	         2.70981610e-01, -8.70554522e-03},
	       { 1.67707488e-01, -2.27301553e-01,  8.77401531e-02,
	        -1.05729192e-01,  1.41825482e-01,  1.56915132e-02,
	         3.05086195e-01,  5.43392301e-02,  1.85249686e-01,
	        -4.21929061e-02, -2.43291453e-01,  1.02559634e-01,
	        -2.63063103e-01,  2.51848608e-01, -1.03893541e-01,
	         2.25519035e-02,  1.98213130e-01, -3.65788415e-02,
	         1.00636534e-01, -1.14028566e-01,  1.83613040e-02,
	         2.50228457e-02,  1.80611640e-01,  2.97586709e-01,
	         6.59416914e-02, -2.23010778e-04, -2.06534743e-01,
	        -2.22068965e-01,  2.79764324e-01,  5.20345941e-02,
	        -2.53244787e-01,  2.32580796e-01},
	       { 6.18489981e-02,  2.17650026e-01,  4.86981869e-02,
	        -2.11638987e-01, -1.01624921e-01,  1.70152962e-01,
	        -4.49147224e-02,  3.04075271e-01, -1.15047529e-01,
	        -8.19001198e-02,  2.92352766e-01, -2.35857844e-01,
	        -1.21998921e-01,  2.30306298e-01,  1.41651899e-01,
	         1.15147620e-01,  2.36353606e-01,  1.95992202e-01,
	         8.03901851e-02, -2.71599412e-01,  2.23874778e-01,
	         2.15941638e-01,  1.44986480e-01,  7.85348117e-02,
	         1.99649781e-01,  2.80102342e-01, -1.13815784e-01,
	         2.86177188e-01, -3.16332877e-02,  4.70938683e-02,
	         1.39175147e-01, -2.54424810e-01},
	       { 5.83923273e-02,  6.02976456e-02,  2.86209285e-01,
	        -1.51373427e-02,  7.25700147e-03,  2.78385967e-01,
	         1.40614048e-01,  1.37738302e-01,  5.74436486e-02,
	         2.69169122e-01, -2.70038456e-01,  2.25980729e-01,
	         6.03867732e-02,  2.55486906e-01,  1.97384983e-01,
	         1.17424265e-01,  1.57192528e-01,  2.67503470e-01,
	        -3.58987227e-03,  2.75226552e-02,  3.02913804e-02,
	        -4.61157337e-02,  1.57655537e-01, -4.99832034e-02,
	        -2.56836087e-01, -1.10100642e-01, -2.88807750e-01,
	        -7.86058009e-02, -1.37220278e-01, -1.93830073e-01,
	         2.77450413e-01,  2.66441852e-01}
	});
	static final Mat b2 = new Mat(new double[][] {
		  { -0.974592  ,  0.81663173, -0.01925546, -0.02983189,  1.1838801 ,
	        -0.02985593,  0.9057482 ,  1.1391938 ,  0.        ,  0.        ,
	        -0.05268283,  0.83401537,  0.80100197,  1.0495461 ,  0.9748064 ,
	         1.4839172 ,  1.0926408 ,  1.293942  , -1.0884355 , -1.0415126 ,
	         0.89261234, -0.00959364, -1.3086842 ,  1.1557475 , -0.02839741,
	         0.        ,  1.0572708 ,  0.2242542 ,  0.88456017, -0.01234555,
	         0.9901415 , -0.9700114 }
	});
	static final Mat W3 = new Mat(new double[][] {
		   {-0.21392779},
	       { 0.10839284},
	       { 0.07075867},
	       {-0.31456017},
	       { 1.0160519 },
	       {-0.31711525},
	       { 0.4464494 },
	       { 0.96299684},
	       {-0.00972703},
	       {-0.05681312},
	       { 0.31390607},
	       { 0.43326297},
	       { 0.1554269 },
	       { 0.8854927 },
	       { 0.44174677},
	       { 0.78902996},
	       { 0.50711155},
	       { 1.3457252 },
	       {-0.3181573 },
	       {-0.25508898},
	       { 0.6049041 },
	       { 0.26955262},
	       {-0.09642296},
	       { 1.1200631 },
	       {-0.3527195 },
	       {-0.12758258},
	       { 0.8190058 },
	       { 0.04010585},
	       { 0.88228637},
	       {-0.10990804},
	       { 0.16480274},
	       {-0.1090544 }
	});
	static final Mat b3 = new Mat(new double[][] {
		   { 0.7659384 }
	});
}