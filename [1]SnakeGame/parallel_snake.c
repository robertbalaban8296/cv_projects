#include "main.h"
#include <stdio.h>
#include <malloc.h>

typedef struct list {
	struct list *next;
	struct coord data;
	int size;
}*List;

List initList(int line, int col) {
	List lista = (List)malloc(sizeof(struct list));
	lista->data.line = line;
	lista->data.col = col;
	lista->next = NULL;
	lista->size = 1;
	return lista;
}

List addLast(List lista, int line, int col) {
	if (lista == NULL) {
		return initList(line, col);
	} else {
		List a = lista;
		while (a->next != NULL) {
			a = a->next;
		}
		lista->size++;
		a->next = initList(line, col);
		return lista;
	}
}

List addFirst(List lista, int line, int col) {
	List l = initList(line, col);
	l->next = lista;
	lista = l;
	return lista;
}

struct coord getLast(List lista) {
	List a = lista;
	if (a == NULL) {
		struct coord data;
		data.line = -1;
		data.col = -1;
		return data;
	} else {
		while (a->next != NULL) {
			a = a->next;
		}
		return a->data;
	}
}

List removeLast(List lista) {
	List a = lista;
	if (a == NULL) {
		return NULL;
	} else {
		if (a->next == NULL) {
			free(a);
			return NULL;
		} else {
			while (a->next->next != NULL) {
				a = a->next;
			}
			List c = a->next;
			a->next = NULL;
			free(c);
			return lista;
		}
	}
}

int contains(List list, int line, int col) {
	List a = list;
	int x = 0;
	while (a != NULL) {
		if (a->data.line == line && a->data.col == col) {
			x = 1;
		}
		a = a->next;
	}
	return x;
}

List eraseContent(List l) {
	List a;
	while (l != NULL) {
		a = l;
		l = l->next;
		free(a);
	}
	return NULL;
}

List getCoords(int **world, int encode, struct coord head, int num_lines, int num_cols) {
	List coords = initList(head.line, head.col);
	int line = head.line;
	int col = head.col;

	while (1 == 1) {
		if (line - 1 >= 0 && world[line - 1][col] == encode && contains(coords, line - 1, col) == 0) {
			coords = addLast(coords, line - 1, col);
			line--;
		} else {
			if (line + 1 < num_lines && world[line + 1][col] == encode && contains(coords, line + 1, col) == 0) {
				coords = addLast(coords, line + 1, col);
				line++;
			} else {
				if (col - 1 >= 0 && world[line][col - 1] == encode && contains(coords, line, col - 1) == 0) {
					coords = addLast(coords, line, col - 1);
					col--;
				} else {
					if (col + 1 < num_cols && world[line][col + 1] == encode && contains(coords, line, col + 1) == 0) {
						coords = addLast(coords, line, col + 1);
						col++;
					} else {
						if (line == 0 && world[num_lines - 1][col] == encode && contains(coords, num_lines - 1, col) == 0) {
							coords = addLast(coords, num_lines - 1, col);
							line = num_lines - 1;
						} else {
							if (line == num_lines - 1 && world[0][col] == encode && contains(coords, 0, col) == 0) {
								coords = addLast(coords, 0, col);
								line = 0;
							} else {
								if (col == 0 && world[line][num_cols - 1] == encode && contains(coords, line ,num_cols - 1) == 0) {
									coords = addLast(coords, line, num_cols - 1);
									col = num_cols - 1;
								} else {
									if (col == num_cols - 1 && world[line][0] == encode && contains(coords, line, 0) == 0) {
										coords = addLast(coords, line, 0);
										col = 0;
									} else {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	return coords;
}

struct coord switchDir(char dir, int line1, int col1, int num_cols, int num_lines) {
	int line = line1;
	int col = col1;
	switch (dir) {
		case 'N':
			if (line - 1 >= 0) {
				line --;
			} else {
				line = num_lines - 1;
				}
			break;
		case 'S':
			if (line + 1 < num_lines) {
				line ++;
			} else {
				line = 0;
			}
			break;
		case 'E':
			if (col + 1 < num_cols) {
				col ++;
			} else {
				col = 0;
			}
			break;
		case 'V':
			if (col - 1 >= 0) {
				col --;
			} else {
				col = num_cols - 1;
			}
			break;
	}
	struct coord rez;
	rez.line = line;
	rez.col = col;
	return rez;
}

void run_simulation(int num_lines, int num_cols, int **world, int num_snakes,
	struct snake *snakes, int step_count, char *file_name) 
{
	int counter = 1;
	List snakePos[num_snakes];
	int coliziune = 0;
	int i;
	int last_line[num_snakes];
	int last_col[num_snakes];


	while (counter <= step_count) {

		#pragma omp parallel for private(i)
		for (i = 0; i < num_snakes; i++) {
			if (counter == 1) {
				snakePos[i] = getCoords(world, snakes[i].encoding, snakes[i].head, num_lines, num_cols);
			}
			struct coord newPos = switchDir(snakes[i].direction, snakes[i].head.line, snakes[i].head.col, num_cols, num_lines);
			struct coord lastPos = getLast(snakePos[i]);
			last_line[i] = lastPos.line;
			last_col[i] = lastPos.col;
		
			if (world[newPos.line][newPos.col] == 0 && coliziune == 0) {
				snakePos[i] = addFirst(snakePos[i], newPos.line, newPos.col);
				snakes[i].head.line = newPos.line;
				snakes[i].head.col = newPos.col;
				world[snakes[i].head.line][snakes[i].head.col] = snakes[i].encoding;
				snakePos[i] = removeLast(snakePos[i]);
				world[lastPos.line][lastPos.col] = 0;
			} else {
				coliziune = 1;
				snakePos[i] = eraseContent(snakePos[i]);
				counter = step_count + 1;
			}
		}
		counter ++;
	}

	if (coliziune == 1) {
		#pragma omp parallel for private(i)
		for (i = 0; i < num_snakes; i++) {
			if (snakePos[i] != NULL) {
				List a = snakePos[i];
				if (a != NULL) {
					world[a->data.line][a->data.col] = 0;
				}
				a = a->next;
				snakes[i].head.line = a->data.line;
				snakes[i].head.col = a->data.col;
				world[last_line[i]][last_col[i]] = snakes[i].encoding;
			}
			snakePos[i] = eraseContent(snakePos[i]);
		}
	}
}
