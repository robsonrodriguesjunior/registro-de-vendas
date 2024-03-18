import dayjs from 'dayjs/esm';

export interface IClient {
  id?: number;
  code?: string | null;
  firstName?: string | null;
  secondName?: string | null;
  birthday?: dayjs.Dayjs | null;
  cpf?: string | null;
}
