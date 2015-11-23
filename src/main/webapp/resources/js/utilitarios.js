function configurarMoeda() {
	$(".moeda").maskMoney({
		decimal : ",",
		thousands : ".",
		allowZero : true
	});
}
$(document).ready(function() {
	configurarMoeda();
});

function painelCadastroEndereco(xhr, status, args) {
	if (args.validationFailed || !args.adicionado) {
		PF('enderecoDetalhesDialog').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('enderecoDetalhesDialog').hide();
	}
};

function painelCadastroContato(xhr, status, args) {
	if (args.validationFailed || !args.adicionado) {
		PF('contatoDetalhesDialog').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('contatoDetalhesDialog').hide();
	}
};

function painelCadastroTelefone(xhr, status, args) {
	if (args.validationFailed || !args.adicionado) {
		PF('telefoneDetalhesDialog').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('telefoneDetalhesDialog').hide();
	}
};

function painelCadastro(xhr, status, args) {
	if (args.validationFailed || !args.adicionado) {
		PF('adicionarDetalhesDialog').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('adicionarDetalhesDialog').hide();
	}
};