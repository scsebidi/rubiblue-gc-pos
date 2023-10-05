var capacitorPlugin = (function (exports, core) {
    'use strict';

    var __awaiter = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
        function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
        return new (P || (P = Promise))(function (resolve, reject) {
            function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
            function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
            function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
            step((generator = generator.apply(thisArg, _arguments || [])).next());
        });
    };
    class GlocellPosModuleWeb extends core.WebPlugin {
        constructor() {
            super({
                name: 'GlocellPosModule',
                platforms: ['web'],
            });
        }
        getSerial(options) {
            return __awaiter(this, void 0, void 0, function* () {
                console.log('getSerial', options);
                return options;
            });
        }
        print(options) {
            return __awaiter(this, void 0, void 0, function* () {
                console.log('print string: ', options.ReceiptText);
                console.log('print string: ', options.ReceiptLogo);
                return {
                    results: [{
                            firstName: 'Dummy',
                            lastName: 'Entry',
                            telephone: '123456'
                        }]
                };
            });
        }
    }
    const GlocellPosModule = new GlocellPosModuleWeb();
    core.registerWebPlugin(GlocellPosModule);

    exports.GlocellPosModule = GlocellPosModule;
    exports.GlocellPosModuleWeb = GlocellPosModuleWeb;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
