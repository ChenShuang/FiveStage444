package fivestage444;

import java.lang.Thread;

public final class Tables {

	public final void init_all (){
		threadInit4Of8.start();
		threadInitParityTable.start();
		threadInitEbmEloc.start();
		threadInitMap96.start();
		threadInitBm12.start();
		threadInitCloc.start();
		threadInitPerm420.start();
		threadInitEdgeStage1.start();
		threadInitCornerStage1.start();
		threadInitCenterStage2.start();
		threadInitEdgeStage2.start();
		threadInitE16Bm.start();
		threadInitCenterStage3.start();
		threadInitEdgeStage3.start();
		threadInitEdgeBStage4.start();
		threadInitEdgeAStage4.start();
		threadInitEdgeRepStage4.start();
		threadInitEdgeStage4.start();
		threadInitCornerStage4.start();
		threadInitCenterStage4.start();
		threadInitSquares2nd.start();
		threadInitSquaresMovemap.start();
		threadInitSquaresCenterMap.start();

		waitAllThreads();
	}

	public final void waitAllThreads (){
		try{
		threadInit4Of8.join();
		threadInitParityTable.join();
		threadInitEbmEloc.join();
		threadInitMap96.join();
		threadInitBm12.join();
		threadInitCloc.join();
		threadInitPerm420.join();
		threadInitEdgeStage1.join();
		threadInitCornerStage1.join();
		threadInitCenterStage2.join();
		threadInitEdgeStage2.join();
		threadInitE16Bm.join();
		threadInitCenterStage3.join();
		threadInitEdgeStage3.join();
		threadInitEdgeBStage4.join();
		threadInitEdgeAStage4.join();
		threadInitEdgeRepStage4.join();
		threadInitEdgeStage4.join();
		threadInitCornerStage4.join();
		threadInitCenterStage4.join();
		threadInitSquares2nd.join();
		threadInitSquaresMovemap.join();
		threadInitSquaresCenterMap.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}
	}

	/*** init_4of8 ***/
	public static final byte[] bm4of8_to_70 = new byte[256];
	public static final short[] bm4of8 = new short[70]; // (256). Was 'byte', now 'short' :(

	private class Init4Of8 extends Thread {

	public void run (){
		System.out.println( "Starting init4of8..." );
		int a1, a2, a3, a4;
		int i;
		int count = 0;
		for (i = 0; i < 256; ++i) {
			bm4of8_to_70[i] = 99;
		}
		for (a1 = 0; a1 < 8-3; ++a1) {
			for (a2 = a1+1; a2 < 8-2; ++a2) {
				for (a3 = a2+1; a3 < 8-1; ++a3) {
					for (a4 = a3+1; a4 < 8; ++a4) {
						bm4of8[count] = (short)((1 << a1) | (1 << a2) | (1 << a3) | (1 << a4));
						bm4of8_to_70[bm4of8[count]] = (byte)count;
						++count;
					}
				}
			}
		}
		System.out.println( "Finishing init4of8..." );
	}

	}

	private Init4Of8 threadInit4Of8 = new Init4Of8();

	/*** init_parity_table ***/
	private static final boolean[] parity_perm8_table = new boolean[40320];

	private static final int get_parity8 (int x){
		int i, j;
		int parity = 0;
		byte[] t = new byte[8];
		Constants.perm_n_unpack (8, x, t, 0);
		for (i = 0; i < 7; ++i) {
			if (t[i] == i) {
				continue;
			}
			for (j = i + 1; j < 8; ++j) {
				if (t[j] == i) {
					//"swap" the i & j elements, but don't bother updating the "i"-element
					//as it isn't needed anymore.
					t[j] = t[i];
				}
			}
			parity ^= 1;
		}
		return parity;
	}

	private class InitParityTable extends Thread {

	public void run (){
		System.out.println( "Starting parity table..." );
		int x;

		for (x = 0; x < 40320; ++x) {
			parity_perm8_table[x] = (get_parity8 (x) != 0);
		}
		System.out.println( "Finishing parity table..." );
	}
	}

	private InitParityTable threadInitParityTable = new InitParityTable();

	/*** init_eloc ***/
	public static final int[] ebm2eloc = new int[4096*4096];
	public static final int[] eloc2ebm = new int[Constants.N_EDGE_COMBO8];

	private static int POW2_24 = 4096*4096;

	private class InitEbmEloc extends Thread {

	public void run (){
		System.out.println( "Starting ebm eloc..." );
		int a1, a2, a3, a4, a5, a6, a7, a8;
		int count = 0;
		for (a1 = 0; a1 < POW2_24; ++a1) {
			ebm2eloc[a1] = 999999;
		}
		for (a1 = 0; a1 < 24-7; ++a1) {
		 for (a2 = a1 + 1; a2 < 24-6; ++a2) {
		  for (a3 = a2 + 1; a3 < 24-5; ++a3) {
		   for (a4 = a3 + 1; a4 < 24-4; ++a4) {
		    for (a5 = a4 + 1; a5 < 24-3; ++a5) {
		     for (a6 = a5 + 1; a6 < 24-2; ++a6) {
		      for (a7 = a6 + 1; a7 < 24-1; ++a7) {
		       for (a8 = a7 + 1; a8 < 24; ++a8) {
		        eloc2ebm[count] = (1 << a1) | (1 << a2) | (1 << a3) | (1 << a4) |
		                          (1 << a5) | (1 << a6) | (1 << a7) | (1 << a8);
		        ebm2eloc[eloc2ebm[count]] = count++;
		       }
		      }
		     }
		    }
		   }
		  }
		 }
		}
		System.out.println( "Finishing ebm eloc..." );
	}
	}

	private InitEbmEloc threadInitEbmEloc = new InitEbmEloc();

	/*** init map96 ***/
	private static final byte[][] map96 = new byte[96][8];

	private class InitMap96 extends Thread {

	public void run (){
		System.out.println( "Starting map 96..." );
		int a1, i;
		byte[] t = new byte[8];
		byte f;
		for (a1 = 0; a1 < 24; ++a1) {
			Constants.perm_n_unpack (4, a1, t, 0); // TODO: Use nextPerm.
			for (i = 0; i < 4; ++i) {
				t[i+4] = (byte)(t[i] + 4);
			}
			for (i = 0; i < 8; ++i) {
				map96[4*a1][i] = t[i];
			}
			f = t[4]; t[4]= t[5]; t[5] = f;
			f = t[6]; t[6]= t[7]; t[7] = f;
			for (i = 0; i < 8; ++i) {
				map96[4*a1 + 1][i] = t[i];
			}
			f = t[4]; t[4]= t[7]; t[7] = f;
			f = t[5]; t[5]= t[6]; t[6] = f;
			for (i = 0; i < 8; ++i) {
				map96[4*a1 + 2][i] = t[i];
			}
			f = t[4]; t[4]= t[5]; t[5] = f;
			f = t[6]; t[6]= t[7]; t[7] = f;
			for (i = 0; i < 8; ++i) {
				map96[4*a1 + 3][i] = t[i];
			}
		}
		System.out.println( "Finishing map 96..." );
	}
	}

	private InitMap96 threadInitMap96 = new InitMap96();

	/*** init bm12 ***/
	private static final int[][] bm12_4of8_to_high_idx = new int[4096][70];
	private static final int[][] bm12_4of8_to_low_idx = new int[4096][70];

	private class InitBm12 extends Thread {

	public void run (){

		try{
			threadInit4Of8.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting bm 12..." );

		int a1, a2;
		int i;
		short u;
		byte[] t = new byte[8];
		// (old) optimization in progress...
		for (u = 0; u < 4096; ++u) {
			int u1;
			for (u1 = 0; u1 < 70; ++u1) {
				int u12 = u;
				int bbbb = bm4of8[u1];
				int j1 = 0;
				int j2;
				for (i = 0; u12 != 0; ++i) {
					if ((u12 & 0x1) != 0) {
						if ((bbbb & 0x1) == 0x1) {
							t[j1++] = (byte)i;
						}
						bbbb >>= 1;
					}
					u12 >>= 1;
				}
				a1 = 0;
				a2 = 24*24*24;
				for (j2 = 0; j2 < j1; ++j2) {
					a1 += a2*t[j2];
					a2 /= 24;
				}
				bm12_4of8_to_low_idx[u][u1] = a1;
				u12 = u;
				bbbb = bm4of8[u1];
				j1 = 0;
				for (i = 24 - 1; u12 != 0; --i) {
					if ((u12 & 0x800) != 0) {
						if ((bbbb & 0x80) != 0) {
							t[j1++] = (byte)i;
						}
						bbbb <<= 1;
					}
					u12 <<= 1;
					u12 &= 0xFFF;		//need this to become 0 after no more than 12 iterations
				}
				a1 = 0;
				a2 = 1;
				for (j2 = 0; j2 < j1; ++j2) {
					a1 += a2*t[j2];
					a2 *= 24;
				}
				bm12_4of8_to_high_idx[u][u1] = a1;
			}
		}
		System.out.println( "Finishing bm 12..." );
	}
	}

	private InitBm12 threadInitBm12 = new InitBm12();

	public static final int swapbits (int x, int b){
		int x2 = x & b;
		if (x2 == 0 || x2 == b) {
			return x;
		}
		return x ^ b;
	}


	/*** init_cloc ***/
	public static final int[] c4_to_cloc = new int[24*24*24*24];
	public static final int[] cloc_to_bm = new int[Constants.N_CENTER_COMBO4];

	private class InitCloc extends Thread {

	public void run (){
		System.out.println( "Starting cloc..." );
		int a1, a2, a3, a4; //, a5, a6, a7, a8;
		int count = 0;

		count = 0;
		for (a1 = 0; a1 < 24-3; ++a1) {
		 for (a2 = a1 + 1; a2 < 24-2; ++a2) {
		  for (a3 = a2 + 1; a3 < 24-1; ++a3) {
		   for (a4 = a3 + 1; a4 < 24; ++a4) {
			cloc_to_bm[count] = (1 << a1) | (1 << a2) | (1 << a3) | (1 << a4);
			c4_to_cloc[24*24*24*a1 + 24*24*a2 + 24*a3 + a4] = count;
			c4_to_cloc[24*24*24*a1 + 24*24*a2 + 24*a4 + a3] = count;
			c4_to_cloc[24*24*24*a1 + 24*24*a3 + 24*a2 + a4] = count;
			c4_to_cloc[24*24*24*a1 + 24*24*a3 + 24*a4 + a2] = count;
			c4_to_cloc[24*24*24*a1 + 24*24*a4 + 24*a2 + a3] = count;
			c4_to_cloc[24*24*24*a1 + 24*24*a4 + 24*a3 + a2] = count;

			c4_to_cloc[24*24*24*a2 + 24*24*a1 + 24*a3 + a4] = count;
			c4_to_cloc[24*24*24*a2 + 24*24*a1 + 24*a4 + a3] = count;
			c4_to_cloc[24*24*24*a2 + 24*24*a3 + 24*a1 + a4] = count;
			c4_to_cloc[24*24*24*a2 + 24*24*a3 + 24*a4 + a1] = count;
			c4_to_cloc[24*24*24*a2 + 24*24*a4 + 24*a1 + a3] = count;
			c4_to_cloc[24*24*24*a2 + 24*24*a4 + 24*a3 + a1] = count;

			c4_to_cloc[24*24*24*a3 + 24*24*a1 + 24*a2 + a4] = count;
			c4_to_cloc[24*24*24*a3 + 24*24*a1 + 24*a4 + a2] = count;
			c4_to_cloc[24*24*24*a3 + 24*24*a2 + 24*a1 + a4] = count;
			c4_to_cloc[24*24*24*a3 + 24*24*a2 + 24*a4 + a1] = count;
			c4_to_cloc[24*24*24*a3 + 24*24*a4 + 24*a1 + a2] = count;
			c4_to_cloc[24*24*24*a3 + 24*24*a4 + 24*a2 + a1] = count;

			c4_to_cloc[24*24*24*a4 + 24*24*a1 + 24*a2 + a3] = count;
			c4_to_cloc[24*24*24*a4 + 24*24*a1 + 24*a3 + a2] = count;
			c4_to_cloc[24*24*24*a4 + 24*24*a2 + 24*a1 + a3] = count;
			c4_to_cloc[24*24*24*a4 + 24*24*a2 + 24*a3 + a1] = count;
			c4_to_cloc[24*24*24*a4 + 24*24*a3 + 24*a1 + a2] = count;
			c4_to_cloc[24*24*24*a4 + 24*24*a3 + 24*a2 + a1] = count;
			++count;
		   }
		  }
		 }
		}
		System.out.println( "Finishing cloc..." );
	}
	}

	private InitCloc threadInitCloc = new InitCloc();

	/*** init_perm_to_420 ***/
	public static final short[] perm_to_420 = new short[40320]; // (420)

	private class InitPerm420 extends Thread {

	public void run (){

		try{
		threadInit4Of8.join();
		threadInitMap96.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting perm 420..." );
		int i;
		int u, v, w, u2;
		byte[] t = new byte[8];
		byte[] t2 = new byte[8];
		byte[] t3 = new byte[8];

		for (u = 0; u < 40320; ++u) {
			perm_to_420[u] = 999;
		}
		for (u = 0; u < 70; ++u) {
			int bm = bm4of8[u];
			for (v = 0; v < 6; ++v) {
				Constants.perm_n_unpack (8, v, t, 0);
					for (w = 0; w < 96; ++w) {
					for (i = 0; i < 8; ++i) {
						t2[i] = map96[w][t[i]];
					}
					int f = 0;
					int b = 4;
					for (i = 0; i < 8; ++i) {
						if ((bm & (1 << i)) == 0) {
							t3[i] = t2[b++];
						} else {
							t3[i] = t2[f++];
						}
					}
					u2 = Constants.perm_n_pack (8, t3, 0);
					perm_to_420[u2] = (short)(6*u + v);
				}
			}
		}
		System.out.println( "Finshing perm 420..." );
	}
	}

	private InitPerm420 threadInitPerm420 = new InitPerm420();

	/*** init stage 1 edges ***/
	public static final int[][] move_table_edgeSTAGE1 = new int[Constants.N_EDGE_COMBO8][Constants.N_BASIC_MOVES]; // (735471) 735471*36 > 100MB !

	private class InitEdgeStage1 extends Thread {

	public void run (){

		try{
		threadInitEbmEloc.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting edge stage 1..." );
		int i, mc;
		byte lrfb, ud;
		int u;
		CubeState cube1 = new CubeState();
		CubeState cube2 = new CubeState();
		cube1.init ();
		cube2.init ();
		for (u = 0; u < Constants.N_EDGE_COMBO8; ++u) {
			int ebm = eloc2ebm[u];
			lrfb = 0;
			ud = 16;
			for (i = 0; i < 24; ++i) {
				if ((ebm & (1 << i)) == 0) {
					cube1.m_edge[i] = lrfb++;
				} else {
					cube1.m_edge[i] = ud++;
				}
			}
			for (mc = 0; mc < Constants.N_BASIC_MOVES; ++mc) {
				System.arraycopy(cube1.m_edge, 0, cube2.m_edge, 0, 24);
				cube2.rotate_sliceEDGE (mc);
				ebm = 0;
				for (i = 0; i < 24; ++i) {
					if (cube2.m_edge[i] >= 16) {
						ebm |= (1 << i);
					}
				}
				move_table_edgeSTAGE1[u][mc] = ebm2eloc[ebm];
			}
		}
		System.out.println( "Finishing edge stage 1..." );
	}
	}

	private InitEdgeStage1 threadInitEdgeStage1 = new InitEdgeStage1();

	/*** init stage 1 corners ***/
	public static final short[][] move_table_co = new short[Constants.N_CORNER_ORIENT][Constants.N_FACE_MOVES]; // (2187) 2187*18

	private class InitCornerStage1 extends Thread {

	public void run (){

		try{
		threadInitEbmEloc.join();
		threadInit4Of8.join();
		threadInitPerm420.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting corner stage 1..." );
		int i, mc;
		int u;
		CubeState cube1 = new CubeState();
		CubeState cube2 = new CubeState();
		cube1.init ();
		cube2.init ();
		CubeStage1 s1 = new CubeStage1();
		CubeStage1 s2 = new CubeStage1();
		s1.init ();
		s2.init ();
		for (u = 0; u < Constants.N_CORNER_ORIENT; ++u) {
			s1.m_co = (short)u;
			s1.convert_to_std_cube (cube1);
			for (mc = 0; mc < Constants.N_BASIC_MOVES; ++mc) {
				int fmc = Constants.basic_to_face[mc];
				if (fmc >= 0) {
					System.arraycopy(cube1.m_cor, 0, cube2.m_cor, 0, 8);
					cube2.rotate_sliceCORNER (mc);
					cube2.convert_to_stage1 (s2);
					move_table_co[u][fmc] = s2.m_co;
				}
			}
		}
		System.out.println( "Finishing corner stage 1..." );
	}
	}

	private InitCornerStage1 threadInitCornerStage1 = new InitCornerStage1();

	/*** init stage 2 centers ***/
	public static final short[][] move_table_cenSTAGE2 = new short[Constants.N_CENTER_COMBO4][Constants.N_STAGE2_SLICE_MOVES]; // 10626*28

	private class InitCenterStage2 extends Thread {

	public void run (){

		try{
		threadInitCloc.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting center stage 2..." );
		int i, j;
		int u;
		byte[] t = new byte[8];
		int mc, udlrf;
		CubeState cube1 = new CubeState();
		CubeState cube2 = new CubeState();
		cube1.init ();
		cube2.init ();
		for (u = 0; u < Constants.N_CENTER_COMBO4; ++u) {
			int cbm = cloc_to_bm[u];
			udlrf = 0;
			for (i = 0; i < 24; ++i) {
				if ((cbm & (1 << i)) == 0) {
					cube1.m_cen[i] = (byte)(udlrf++/4);
				} else {
					cube1.m_cen[i] = 5;
				}
			}
			for (mc = 0; mc < Constants.N_STAGE2_SLICE_MOVES; ++mc) {
				System.arraycopy(cube1.m_cen, 0, cube2.m_cen, 0, 24);
				cube2.rotate_sliceCENTER (Constants.stage2_slice_moves[mc]);
				j = 0;
				for (i = 0; i < 24; ++i) {
					if (cube2.m_cen[i] == 5) {
						t[j++] = (byte)i;
					}
				}
				int idx = 24*24*24*t[0] + 24*24*t[1] + 24*t[2] + t[3];
				move_table_cenSTAGE2[u][mc] = (short)c4_to_cloc[idx];
			}
		}
		System.out.println( "Finishing center stage 2..." );
	}
	}

	private InitCenterStage2 threadInitCenterStage2 = new InitCenterStage2();

	/*** init stage 2 edges ***/
	public static final short[][] move_table_edgeSTAGE2 = new short[Constants.N_STAGE2_EDGE_CONFIGS][Constants.N_STAGE2_SLICE_MOVES]; // 420*28

	private class InitEdgeStage2 extends Thread {

	public void run (){

		try{
		threadInitEbmEloc.join();
		threadInit4Of8.join();
		threadInitPerm420.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting edge stage 2..." );
		short u;
		int mc;
		CubeStage2 s1 = new CubeStage2();
		for (u = 0; u < Constants.N_STAGE2_EDGE_CONFIGS; ++u) {
			for (mc = 0; mc < Constants.N_STAGE2_SLICE_MOVES; ++mc) {
				s1.m_edge = u;
				s1.do_move_slow (mc);
				move_table_edgeSTAGE2[u][mc] = s1.m_edge;
			}
		}
		System.out.println( "Finishing edge stage 2..." );
	}
	}

	private InitEdgeStage2 threadInitEdgeStage2 = new InitEdgeStage2();

	public static final int stage2_cen_to_cloc4sf (int cen){
		int cenbm = eloc2ebm[cen / 70];
		int idx1 = bm12_4of8_to_high_idx[cenbm >> 12][cen % 70];
		idx1 += bm12_4of8_to_low_idx[cenbm & 0xFFF][cen % 70];
		return c4_to_cloc[idx1];
	}

	public static final int stage2_cen_to_cloc4sb (int cen){
		int cenbm = eloc2ebm[cen / 70];
		int cenbm4of8 = bm4of8[cen % 70];
		int comp_70 = bm4of8_to_70[(~cenbm4of8) & 0xFF];	// (old) could be a direct lookup
		int idx2 = bm12_4of8_to_high_idx[cenbm >> 12][comp_70];
		idx2 += bm12_4of8_to_low_idx[cenbm & 0xFFF][comp_70];
		return c4_to_cloc[idx2];
	}

	/*** init_stage3 ***/
	public static final int[] e16bm2eloc = new int[256*256];
	public static final int[] eloc2e16bm = new int[Constants.N_COMBO_16_8];

	private static int POW2_16 = 256*256;

	private class InitE16Bm extends Thread {

	public void run (){
		System.out.println( "Starting e16bm..." );
		int a1, a2, a3, a4, a5, a6, a7, a8;

		int count = 0;
		for (a1 = 0; a1 < POW2_16; ++a1) {
			e16bm2eloc[a1] = 999999;
		}
		for (a1 = 0; a1 < 16-7; ++a1) {
		 for (a2 = a1 + 1; a2 < 16-6; ++a2) {
		  for (a3 = a2 + 1; a3 < 16-5; ++a3) {
		   for (a4 = a3 + 1; a4 < 16-4; ++a4) {
		    for (a5 = a4 + 1; a5 < 16-3; ++a5) {
		     for (a6 = a5 + 1; a6 < 16-2; ++a6) {
		      for (a7 = a6 + 1; a7 < 16-1; ++a7) {
		       for (a8 = a7 + 1; a8 < 16; ++a8) {
		        eloc2e16bm[count] = (1 << a1) | (1 << a2) | (1 << a3) | (1 << a4) |
		                            (1 << a5) | (1 << a6) | (1 << a7) | (1 << a8);
		        e16bm2eloc[eloc2e16bm[count]] = count++;
		       }
		      }
		     }
		    }
		   }
		  }
		 }
		}
		System.out.println( "Finishing e16bm..." );
	}
	}

	private InitE16Bm threadInitE16Bm = new InitE16Bm();

	public static final int[][] move_table_cenSTAGE3 = new int[Constants.N_STAGE3_CENTER_CONFIGS][Constants.N_STAGE3_SLICE_MOVES]; // 900900*20 = 72MB

	private class InitCenterStage3 extends Thread {

	public void run (){

		try{
		threadInitE16Bm.join();
		threadInit4Of8.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting center stage 3..." );
		int mc;
		int u;
		CubeStage3 s3 = new CubeStage3();
		CubeState cube1 = new CubeState();
		s3.init ();
		cube1.init ();
		for (u = 0; u < Constants.N_STAGE3_CENTER_CONFIGS; ++u) {
			for (mc = 0; mc < Constants.N_STAGE3_SLICE_MOVES; ++mc) {
				s3.m_centerLR = u;
				s3.convert_centers_to_std_cube(cube1);
				cube1.rotate_sliceCENTER(Constants.stage3_slice_moves[mc]);
				cube1.convert_centers_to_stage3 (s3);
				move_table_cenSTAGE3[u][mc] = s3.m_centerLR;
			}
		}
		System.out.println( "Finishing center stage 3..." );
	}
	}

	private InitCenterStage3 threadInitCenterStage3 = new InitCenterStage3();

	public static final short[][] move_table_edgeSTAGE3 = new short[Constants.N_STAGE3_EDGE_CONFIGS][Constants.N_STAGE3_SLICE_MOVES]; // (12870) 12870*20

	private class InitEdgeStage3 extends Thread {

	public void run (){

		try{
		threadInitE16Bm.join();
		threadInit4Of8.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting edge stage 3..." );
		int mc;
		int u;
		CubeStage3 s3 = new CubeStage3();
		s3.init ();
		for (u = 0; u < Constants.N_STAGE3_EDGE_CONFIGS; ++u) {
			s3.m_centerLR = 0;
			for (mc = 0; mc < Constants.N_STAGE3_SLICE_MOVES; ++mc) {
				s3.m_edge = (short)u;
				s3.do_move_slow (mc);
				move_table_edgeSTAGE3[u][mc] = s3.m_edge;
			}
		}
		System.out.println( "Finishing edge stage 3..." );
	}
	}

	private InitEdgeStage3 threadInitEdgeStage3 = new InitEdgeStage3();

	/*** init_stage4 ***/
	private static final int sqs_rep_to_perm[][] = {
		{  0,  7, 16, 23 },
		{  1,  6, 17, 22 },
		{  2, 10, 13, 21 },
		{  3, 11, 12, 20 },
		{  4,  8, 15, 19 },
		{  5,  9, 14, 18 }
	};

	private static final void array8_to_set_a (byte[] t, CubeState result_cube){
		int i;
		int j = 0;
		for (i = 0; i < 8; ++i) {
			if (i >= 4) {
				j = i + 8;
			} else {
				j = i;
			}
			byte t1 = t[i];
			if (t1 >= 4) {
				t1 += 8;
			}
			result_cube.m_edge[j] = t1;
		}
	}

	private static final void array8_to_set_b (byte[] t, CubeState result_cube){
		int i;
		for (i = 0; i < 8; ++i) {
			result_cube.m_edge[4 + i] = (byte)(t[i] + 4);
		}
	}


	public static final void lrfb_to_cube_state (int u, CubeState result_cube){
		byte[] t = new byte[8];
		result_cube.init ();
		Constants.perm_n_unpack (8, u % 40320, t, 0);
		array8_to_set_a (t, result_cube);
		Constants.perm_n_unpack (8, u / 40320, t, 0);
		array8_to_set_b (t, result_cube);
	}

	private static final int[] stage4_edge_hB = new int[40320]; // (40320 ?). Change from short to int then :(
	private static final int[] stage4_edge_hgB = new int[40320]; // (40320 ?). Change from short to int then :(

	private class InitEdgeBStage4 extends Thread {

	public void run (){
		System.out.println( "Starting edge B stage 4..." );
		byte i;
		int u;
		CubeState cs1 = new CubeState();
		CubeState cs2 = new CubeState();
		CubeState cs3 = new CubeState();
		cs1.init ();
		cs2.init ();
		for (i = 0; i < 16; ++i ){
			cs2.m_edge[i] = i;
		}
		for (u = 0; u < 40320; ++u) {
			// lrfb_to_cube_state (40320*u, cs2); // Replaced by a nextPerm
			int rep = 999;
			int reph = 65000;
			int Blr, Bfb;

			for (i = 4; i < 8; ++i) {
				cs1.m_edge[i] = i;
			}

			for (Blr = 0; Blr < 24; ++Blr) {

				for (i = 8; i < 12; ++i) {
					cs1.m_edge[i] = i;
				}

				for (Bfb = 0; Bfb < 24; ++Bfb) {
					cs3.compose_edge (cs1, cs2);
					int u3h = cs3.cube_state_to_lrfb_h ();
					if (u3h < reph) {
						reph = u3h;
						rep = 24 * Blr + Bfb;
					}
					Constants.nextPerm( cs1.m_edge, 4, 8 );
				}
				Constants.nextPerm( cs1.m_edge, 4, 4 );
			}
			stage4_edge_hgB[u] = reph;
			int repBlr = sqs_perm_to_rep[rep/24];
			int repBfb = sqs_perm_to_rep[rep%24];
			stage4_edge_hB[u] = 6*repBlr + repBfb;
			Constants.nextPerm( cs2.m_edge, 8, 4 );
		}
		System.out.println( "Finishing edge B stage 4..." );
	}
	}

	private InitEdgeBStage4 threadInitEdgeBStage4 = new InitEdgeBStage4();

	private static final int[][] stage4_edge_hgA = new int[40320][36]; // (40320 ?). Change from short to int then :(

	private class InitEdgeAStage4 extends Thread {

	public void run (){
		System.out.println( "Starting edge A stage 4..." );
		int i;
		int u, h1, h2;
		CubeState cs1 = new CubeState();
		CubeState cs2 = new CubeState();
		CubeState cs3 = new CubeState();
		cs1.init ();
		cs2.init ();
		for (u = 0; u < 40320; ++u) {
			lrfb_to_cube_state (u, cs2);
			for (h1 = 0; h1 < 36; ++h1) {
				int repl = 65000;
				int replr = h1 / 6;
				int repfb = h1 % 6;
				for (h2 = 0; h2 < 16; ++h2) {
					Constants.perm_n_unpack (4, sqs_rep_to_perm[replr][h2%4], cs1.m_edge, 0);
					if(( h2 % 4 ) == 0){ // Only need to update once every 4 iterations
						Constants.perm_n_unpack (4, sqs_rep_to_perm[repfb][h2/4], cs1.m_edge, 12);
						for (i = 12; i < 16; ++i) {
							cs1.m_edge[i] += 12;
						}
					}
					cs3.compose_edge (cs1, cs2);
					int u3l = cs3.cube_state_to_lrfb_l();
					if (u3l < repl) {
						repl = u3l;
					}
				}
				stage4_edge_hgA[u][h1] = repl;
			}
		}
		System.out.println( "Finishing edge A stage 4..." );
	}
	}

	private InitEdgeAStage4 threadInitEdgeAStage4 = new InitEdgeAStage4();

	private static int[] stage4_edge_hash_table_val = new int[Constants.N_STAGE4_EDGE_HASH_TABLE]; // (200383)
	public static int[] stage4_edge_hash_table_idx = new int[Constants.N_STAGE4_EDGE_HASH_TABLE]; // (200383)
	public static final int[] stage4_edge_rep_table = new int[Constants.N_STAGE4_EDGE_CONFIGS]; // (88200)

	public static final int lrfb_get_edge_rep (int u){
		int reph = stage4_edge_hgB[u/40320];
		int repl = stage4_edge_hgA[u % 40320][stage4_edge_hB[u/40320]];
		return 40320*reph + repl;
	}

	public static void stage4_edge_table_init (){
		int i;
		for (i = 0; i < Constants.N_STAGE4_EDGE_HASH_TABLE; ++i) {
			stage4_edge_hash_table_val[i] = 40320*40320;	//an "invalid" value
			stage4_edge_hash_table_idx[i] = 88200;	//also "invalid" but shouldn't matter
		}
	}

	public static int stage4_edge_table_lookup (int val){
		int hash = val % Constants.N_STAGE4_EDGE_HASH_DIVISOR;
		int i = hash + 1;
		while (stage4_edge_hash_table_val[i] < 40320*40320) {
			if (stage4_edge_hash_table_val[i] == val) {
				return i;
			}
			i += hash;
			i %= Constants.N_STAGE4_EDGE_HASH_TABLE;
			if (i == 0) {	//relies on table being a prime number in size
				return 0;	//not found, table full, return 0
			}
		}
		return i;	//new position, it was not found in the table
	}

	private static void add_to_stage4_edge_table (int val, int idx){
		int hash_idx = stage4_edge_table_lookup (val);
		stage4_edge_hash_table_val[hash_idx] = val;
		stage4_edge_hash_table_idx[hash_idx] = idx;
		stage4_edge_rep_table[idx] = val;
	}

	private class InitEdgeRepStage4 extends Thread {
	public void run (){

		try{
		threadInitEdgeBStage4.join();
		threadInitEdgeAStage4.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting edge rep stage 4..." );
		int u1;
		CubeState cs1 = new CubeState();
		CubeState cs2 = new CubeState();
		cs1.init ();
		cs2.init ();

		stage4_edge_table_init ();
		int repcount = 0;
		int n = 40320*40320;
		for (u1 = 0; u1 < n; ++u1) {
			if ((u1 << 22 ) == 0) { // Throughly u1 % 1000 == 0
				if (repcount == 44100 && u1 < 105262000) { // Obtained though execution
					u1 = 40320*20160;
				}
				if (repcount == 88200) {
					break;
				}
			}
			int uH = u1 / 40320;
			int uL = u1 % 40320;
			if (parity_perm8_table[uH] != parity_perm8_table[uL]) {
				continue;
			}
			int myrep = lrfb_get_edge_rep (u1);
			if (myrep == u1) {
				add_to_stage4_edge_table (myrep, repcount++);
			}
		}
		System.out.println( "Finishing edge rep stage 4..." );
	}
	}

	private InitEdgeRepStage4 threadInitEdgeRepStage4 = new InitEdgeRepStage4();

	public static final int[][] move_table_edgeSTAGE4 = new int[Constants.N_STAGE4_EDGE_CONFIGS][Constants.N_STAGE4_SLICE_MOVES]; // (88200) 88200*16.

	private class InitEdgeStage4 extends Thread {
	public void run (){

		try{
		threadInit4Of8.join();
		threadInitEdgeRepStage4.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting edge stage 4..." );
		int mc;
		int u;
		CubeStage4 s4 = new CubeStage4();
		CubeStage4 s4a = new CubeStage4();
		s4.init ();
		s4a.init ();
		CubeState cs1 = new CubeState();
		cs1.init ();
		for (u = 0; u < Constants.N_STAGE4_EDGE_CONFIGS; ++u) {
			for (mc = 0; mc < Constants.N_STAGE4_SLICE_MOVES; ++mc) {
				s4.m_edge = u;
				s4.convert_to_std_cube (cs1);
				cs1.rotate_sliceEDGE (Constants.stage4_slice_moves[mc]);
				int u2 = cs1.cube_state_to_lrfb ();
				int edgerep = lrfb_get_edge_rep (u2);
				int hash_idx = stage4_edge_table_lookup (edgerep);
				move_table_edgeSTAGE4[u][mc] = stage4_edge_hash_table_idx[hash_idx];
			}
		}
		System.out.println( "Finishing edge stage 4..." );
	}
	}

	private InitEdgeStage4 threadInitEdgeStage4 = new InitEdgeStage4();

	public static final short[][] move_table_cornerSTAGE4 = new short[Constants.N_STAGE4_CORNER_CONFIGS][Constants.N_STAGE4_SLICE_MOVES]; // (420) 420*16.

	private class InitCornerStage4 extends Thread {
	public void run (){

		try{
		threadInit4Of8.join();
		threadInitPerm420.join();
		threadInitEdgeRepStage4.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting corner stage 4..." );
		int mc;
		int u;
		CubeStage4 s4 = new CubeStage4();
		CubeStage4 s4a = new CubeStage4();
		s4.init ();
		s4a.init ();
		CubeState cs1 = new CubeState();
		cs1.init ();
		for (u = 0; u < Constants.N_STAGE4_CORNER_CONFIGS; ++u) {
			for (mc = 0; mc < Constants.N_STAGE4_SLICE_MOVES; ++mc) {
				s4.m_corner = (short)u;
				s4.convert_to_std_cube (cs1);
				cs1.rotate_sliceCORNER (Constants.stage4_slice_moves[mc]);
				cs1.convert_to_stage4 (s4a);
				move_table_cornerSTAGE4[u][mc] = s4a.m_corner;
			}
		}
		System.out.println( "Finishing corner stage 4..." );
	}
	}

	private InitCornerStage4 threadInitCornerStage4 = new InitCornerStage4();

	public static final byte[][] move_table_cenSTAGE4 = new byte[Constants.N_STAGE4_CENTER_CONFIGS][Constants.N_STAGE4_SLICE_MOVES]; // (70) 70*16.

	private class InitCenterStage4 extends Thread {
	public void run (){

		try{
		threadInit4Of8.join();
		threadInitPerm420.join();
		threadInitEdgeRepStage4.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting center stage 4..." );
		int mc;
		int u;
		CubeStage4 s4 = new CubeStage4();
		CubeStage4 s4a = new CubeStage4();
		s4.init ();
		s4a.init ();
		CubeState cs1 = new CubeState();
		cs1.init ();
		for (u = 0; u < Constants.N_STAGE4_CENTER_CONFIGS; ++u) {
			for (mc = 0; mc < Constants.N_STAGE4_SLICE_MOVES; ++mc) {
				s4.m_centerUD = (byte)u;
				s4.convert_to_std_cube (cs1);
				cs1.rotate_sliceCENTER (Constants.stage4_slice_moves[mc]);
				cs1.convert_to_stage4 (s4a);
				move_table_cenSTAGE4[u][mc] = s4a.m_centerUD;
			}
		}
		System.out.println( "Finishing center stage 4..." );
	}
	}

	private InitCenterStage4 threadInitCenterStage4 = new InitCenterStage4();

	/*** init_stage5 ***/
	//map a "squares" move code to one of six "canonical" move codes,
	//or -1 for moves that don't affect the corresponding pieces.
	private static final int squares_map[][] = {
		{  0, -1,  1, -1, -1,  2, -1,  3,  4, -1,  5, -1 },		//LR edges
		{  4, -1,  5, -1,  0, -1,  1, -1, -1,  2, -1,  3 },		//FB edges
		{ -1,  2, -1,  3,  4, -1,  5, -1,  0, -1,  1, -1 },		//UD edges
		{  0, -1,  1, -1,  2, -1,  3, -1,  4, -1,  5, -1 },		//corners
		{  0, -1,  1, -1, -1,  2, -1,  3, -1,  4, -1,  5 },		//UD centers
		{ -1,  4, -1,  5,  0, -1,  1, -1, -1,  2, -1,  3 },		//LR centers
		{ -1,  2, -1,  3, -1,  4, -1,  5,  0, -1,  1, -1 }		//FB centers
	};

	private static final short squares_cen_map[] = { 0x0F, 0x33, 0x3C, 0x55, 0x5A, 0x66, 0x99, 0xA5, 0xAA, 0xC3, 0xCC, 0xF0 };

	private static final int sqs_perm_to_rep[] = {
		0, 1, 2, 3, 4, 5,
		1, 0, 4, 5, 2, 3,
		3, 2, 5, 4, 0, 1,
		5, 4, 3, 2, 1, 0
	};

	private static int mov_lst[] = { Constants.Uf2, Constants.Df2, Constants.Ls2, Constants.Rs2, Constants.Ff2, Constants.Bf2 };
	private static short cen_swapbits_map[] = {
		0x90, 0x60, //Uf2
		0x09, 0x06, //Df2
		0x82, 0x28, //Ls2
		0x41, 0x14, //Rs2
		0x84, 0x48, //Fs2
		0x21, 0x12  //Bs2
	};

	private static final int[][] squares_2nd_perm = new int[24][4];

	private class InitSquares2nd extends Thread {
	public void run (){
		System.out.println( "Starting squares 2nd perm..." );
		int i;
		for (i = 0; i < 24; ++i) {
			switch (sqs_perm_to_rep[i]) {
			case 0:
				squares_2nd_perm[i][0] = 0;
				squares_2nd_perm[i][1] = 7;
				squares_2nd_perm[i][2] = 16;
				squares_2nd_perm[i][3] = 23;
				break;
			case 1:
				squares_2nd_perm[i][0] = 1;
				squares_2nd_perm[i][1] = 6;
				squares_2nd_perm[i][2] = 17;
				squares_2nd_perm[i][3] = 22;
				break;
			case 2:
				squares_2nd_perm[i][0] = 2;
				squares_2nd_perm[i][1] = 10;
				squares_2nd_perm[i][2] = 13;
				squares_2nd_perm[i][3] = 21;
				break;
			case 3:
				squares_2nd_perm[i][0] = 3;
				squares_2nd_perm[i][1] = 11;
				squares_2nd_perm[i][2] = 12;
				squares_2nd_perm[i][3] = 20;
				break;
			case 4:
				squares_2nd_perm[i][0] = 4;
				squares_2nd_perm[i][1] = 8;
				squares_2nd_perm[i][2] = 15;
				squares_2nd_perm[i][3] = 19;
				break;
			case 5:
				squares_2nd_perm[i][0] = 5;
				squares_2nd_perm[i][1] = 9;
				squares_2nd_perm[i][2] = 14;
				squares_2nd_perm[i][3] = 18;
				break;
			}
		}
		System.out.println( "Finishing squares 2nd perm..." );
	}
	}

	private InitSquares2nd threadInitSquares2nd = new InitSquares2nd();

	private static final byte[][] squares_movemap = new byte[96][6]; // (96 ?)

	private class InitSquaresMovemap extends Thread {
	public void run (){

		try{
		threadInitSquares2nd.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting squares movemap..." );
		int i, j, k, first_perm = 0, second_perm;
		CubeState cube1 = new CubeState();
		CubeState cube2 = new CubeState();
		cube2.init ();
		for (i = 0; i < 96; ++i) {
			if(( i % 4 ) == 0 ){ // Only need to update once every 4 iterations.
				first_perm = i / 4;
				Constants.perm_n_unpack (4, first_perm, cube1.m_edge, 0);
			}
			second_perm = squares_2nd_perm[first_perm][i % 4];
			Constants.perm_n_unpack (4, second_perm, cube1.m_edge, 4);
			for (j = 4; j < 8; ++j) {
				cube1.m_edge[j] += 4;
			}
			for (j = 0; j < 6; ++j) {
				System.arraycopy(cube1.m_edge, 0, cube2.m_edge, 0, 8);
				cube2.rotate_sliceEDGE (mov_lst[j]);
				int x1 = Constants.perm_n_pack (4, cube2.m_edge, 0);
				int x2 = cube2.m_edge[4] - 4;
				squares_movemap[i][j] = (byte)(4*x1 + x2);
			}
		}
		System.out.println( "Finishing squares movemap..." );
	}
	}

	private InitSquaresMovemap threadInitSquaresMovemap = new InitSquaresMovemap();

	public static final byte[] squares_cen_revmap = new byte[256]; // (12)
	private static final byte[][] squares_cen_movemap = new byte[12][6]; // (12)

	private class InitSquaresCenterMap extends Thread {
	public void run (){

		try{
		threadInitSquaresMovemap.join();
		}
		catch(InterruptedException ie){
			ie.printStackTrace();
		}

		System.out.println( "Starting squares center map..." );
		int i, j;
		for (i = 0; i < 256; ++i) {
			squares_cen_revmap[i] = 0;
		}
		for (i = 0; i < 12; ++i) {
			squares_cen_revmap[squares_cen_map[i]] = (byte)i;
		}
		for (i = 0; i < 12; ++i) {
			int x = squares_cen_map[i];
			for (j = 0; j < 6; ++j) {
				int x2 = swapbits (x, cen_swapbits_map[2*j]);
				x2 = swapbits (x2, cen_swapbits_map[2*j + 1]);
				squares_cen_movemap[i][j] = squares_cen_revmap[x2];
			}
		}
		System.out.println( "Finishing squares center map..." );
	}
	}

	private InitSquaresCenterMap threadInitSquaresCenterMap = new InitSquaresCenterMap();

	public static final int squares_move (int pos96, int move_code6){
		if (move_code6 < 0) {
			return pos96;
		}
		return squares_movemap[pos96][move_code6];
	}

	public static final int squares_move_corners (int pos96, int sqs_move_code){
		return squares_move (pos96, squares_map[3][sqs_move_code]);
	}

	public static final int squares_move_edges (int pos96, int sqs_move_code, int edge_group){
		return squares_move (pos96, squares_map[edge_group][sqs_move_code]);
	}

	public static final int squares_move_centers (int pos12, int sqs_move_code, int cen_group){
		int move_code6 = squares_map[4+cen_group][sqs_move_code];
		if (move_code6 < 0) {
			return pos12;
		}
		return squares_cen_movemap[pos12][move_code6];
	}

}
