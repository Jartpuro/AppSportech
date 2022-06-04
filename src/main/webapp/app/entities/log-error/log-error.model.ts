import dayjs from 'dayjs/esm';
import { ICustomer } from 'app/entities/customer/customer.model';

export interface ILogError {
  id?: number;
  levelError?: string;
  logName?: string;
  messageError?: string;
  dateError?: dayjs.Dayjs;
  customer?: ICustomer;
}

export class LogError implements ILogError {
  constructor(
    public id?: number,
    public levelError?: string,
    public logName?: string,
    public messageError?: string,
    public dateError?: dayjs.Dayjs,
    public customer?: ICustomer
  ) {}
}

export function getLogErrorIdentifier(logError: ILogError): number | undefined {
  return logError.id;
}
