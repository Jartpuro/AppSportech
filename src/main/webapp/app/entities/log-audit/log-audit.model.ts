import dayjs from 'dayjs/esm';
import { ICustomer } from 'app/entities/customer/customer.model';

export interface ILogAudit {
  id?: number;
  levelAudit?: string;
  logName?: string;
  messageAudit?: string;
  dateAudit?: dayjs.Dayjs;
  customer?: ICustomer;
}

export class LogAudit implements ILogAudit {
  constructor(
    public id?: number,
    public levelAudit?: string,
    public logName?: string,
    public messageAudit?: string,
    public dateAudit?: dayjs.Dayjs,
    public customer?: ICustomer
  ) {}
}

export function getLogAuditIdentifier(logAudit: ILogAudit): number | undefined {
  return logAudit.id;
}
