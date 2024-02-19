import dayjs from 'dayjs/esm';
import { IClient } from 'app/entities/client/client.model';
import { ICollaborator } from 'app/entities/collaborator/collaborator.model';

export interface IPerson {
  id: number;
  firstName?: string | null;
  secondName?: string | null;
  birthday?: dayjs.Dayjs | null;
  client?: IClient | null;
  seller?: ICollaborator | null;
}

export type NewPerson = Omit<IPerson, 'id'> & { id: null };
